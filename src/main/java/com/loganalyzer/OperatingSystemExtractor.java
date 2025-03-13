package com.loganalyzer;

import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.OperatingSystem;



class OperatingSystemExtractor implements MetricExtractor {
    @Override
    public String getMetricName() {
        return "os";
    }

    @Override
    public String extract(String logLine) {
        String userAgentString = LogEntryParser.extractUserAgent(logLine);
        if (!userAgentString.isEmpty()) {
            UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
            OperatingSystem os = userAgent.getOperatingSystem();

            if (os.getGroup() == OperatingSystem.WINDOWS) {
                return "Windows";
            } else if (os.getGroup() == OperatingSystem.MAC_OS) {
                return "macOS";
            } else if (os.getGroup() == OperatingSystem.IOS) {
                return "iOS";
            } else if (os.getGroup() == OperatingSystem.ANDROID) {
                return "Android";
            } else if (os.getGroup() == OperatingSystem.LINUX) {
                return "Linux";
            }
            return os.getName();
        }
        return "";
    }
}
