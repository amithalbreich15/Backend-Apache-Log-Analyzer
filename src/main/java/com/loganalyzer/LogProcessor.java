package com.loganalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.maxmind.geoip2.DatabaseReader;

class LogProcessor implements AutoCloseable {
    private final Map<String, Map<String, Integer>> metrics;
    private final List<MetricExtractor> extractors;
    private final DatabaseReader geoIpReader;

    public LogProcessor(File geoIpDatabase) throws IOException {
        this.metrics = new HashMap<>();
        this.extractors = new ArrayList<>();
        this.geoIpReader = new DatabaseReader.Builder(geoIpDatabase).build();

        registerExtractor(new CountryExtractor(geoIpReader));
        registerExtractor(new OperatingSystemExtractor());
        registerExtractor(new BrowserExtractor());
    }

    public void registerExtractor(MetricExtractor extractor) {
        extractors.add(extractor);
        metrics.put(extractor.getMetricName(), new HashMap<>());
    }

    public void processLogFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                processLogLine(line);
            }
        }
    }

    private void processLogLine(String logLine) {
        for (MetricExtractor extractor : extractors) {
            String value = extractor.extract(logLine);
            if (value != null && !value.isEmpty()) {
                Map<String, Integer> metricData = metrics.get(extractor.getMetricName());
                metricData.put(value, metricData.getOrDefault(value, 0) + 1);
            }
        }
    }

    public Map<String, Integer> getMetric(String metricName) {
        return metrics.getOrDefault(metricName, Collections.emptyMap());
    }

    @Override
    public void close() {
        try {
            if (geoIpReader != null) {
                geoIpReader.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing GeoIP database: " + e.getMessage());
        }
    }
}
