package com.loganalyzer;

import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Browser;

class BrowserExtractor implements MetricExtractor {
    @Override
    public String getMetricName() {
        return "browser";
    }

    @Override
    public String extract(String logLine) {
        String userAgentString = LogEntryParser.extractUserAgent(logLine);
        if (!userAgentString.isEmpty()) {
            UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
            Browser browser = userAgent.getBrowser();

            if (browser.getGroup() == Browser.FIREFOX) {
                return "Firefox";
            } else if (browser.getGroup() == Browser.CHROME) {
                return "Chrome";
            } else if (browser.getGroup() == Browser.IE) {
                return "IE";
            } else if (browser.getGroup() == Browser.SAFARI) {
                return "Safari";
            } else if (browser.getGroup() == Browser.EDGE) {
                return "Edge";
            } else if (browser.getName().contains("Bot") ||
                    browser.getName().contains("Crawler") ||
                    browser.getName().contains("Spider")) {
                return "Bot";
            }
            return browser.getName();
        }
        return "";
    }
}
