package com.loganalyzer;

import java.io.IOException;
import java.net.InetAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CountryResponse;


class CountryExtractor implements MetricExtractor {
    private final Pattern ipPattern = Pattern.compile("^(\\d+\\.\\d+\\.\\d+\\.\\d+)");
    private final DatabaseReader geoIpReader;

    public CountryExtractor(DatabaseReader geoIpReader) {
        this.geoIpReader = geoIpReader;
    }

    @Override
    public String getMetricName() {
        return "country";
    }

    @Override
    public String extract(String logLine) {
        Matcher matcher = ipPattern.matcher(logLine);
        if (matcher.find()) {
            String ip = matcher.group(1);
            try {
                InetAddress ipAddress = InetAddress.getByName(ip);
                CountryResponse response = geoIpReader.country(ipAddress);
                return response.getCountry().getName();
            } catch (IOException | GeoIp2Exception e) {
                // If we can't get the country, return Unknown
                return "Unknown";
            }
        }
        return "Unknown";
    }
}
