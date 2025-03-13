package com.loganalyzer;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class LogAnalyzer {
    public static void main(String[] args) {

        String geoLiteDbPath = "src/main/resources/GeoLite2-Country.mmdb"; // Path to the GeoIP database

        String fileUrl = "https://grnhse-use1-prod-s1-ghr.s3.amazonaws.com/generic_attachments/attachments/002/693/931/original/all.log?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=AKIAVQGOLGY373LJL5PF%2F20250306%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20250306T130529Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=7cd1b62143623b64595973d608855416f8711ca59d39c578c1bd8f96b620419e";
        String filePath = "src/main/resources/all.log";

        LogFileDownloader downloader = new LogFileDownloader();
        downloader.downloadFile(fileUrl, filePath);

        LogProcessor processor = null;
        try {
            // Initialize with the GeoIP database
            processor = new LogProcessor(new File(geoLiteDbPath));
            processor.processLogFile(filePath);

            // Generate and print reports
            System.out.println("Countries:");
            printMetricReport(processor.getMetric("country"));

            System.out.println("\nOperating systems:");
            printMetricReport(processor.getMetric("os"));

            System.out.println("\nBrowsers:");
            printMetricReport(processor.getMetric("browser"));

        } catch (IOException e) {
            System.err.println("Error processing log file: " + e.getMessage());
        } finally {
            if (processor != null) {
                processor.close();
            }
        }
    }

    private static void printMetricReport(Map<String, Integer> metricData) {
        if (metricData == null || metricData.isEmpty()) {
            System.out.println("No data available");
            return;
        }

        int total = metricData.values().stream().mapToInt(Integer::intValue).sum();

        // Convert to percentage entries
        List<Map.Entry<String, Double>> percentages = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : metricData.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / total;
            percentages.add(new AbstractMap.SimpleEntry<>(entry.getKey(), percentage));
        }

        percentages.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));

        for (Map.Entry<String, Double> entry : percentages) {
            System.out.printf("%s - %.2f%%\n", entry.getKey(), entry.getValue());
        }
    }
}

