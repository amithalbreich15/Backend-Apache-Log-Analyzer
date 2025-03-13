package com.loganalyzer;

interface MetricExtractor {
    String getMetricName();

    String extract(String logLine);
}
