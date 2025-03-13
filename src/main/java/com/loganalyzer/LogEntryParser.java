package com.loganalyzer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class LogEntryParser {
    private static final Pattern LOG_PATTERN = Pattern.compile("^\\S+ - - \\[.*?\\] \".*?\" \\d+ \\d+ \".*?\" \"(.*?)\"");

    public static String extractUserAgent(String logLine) {
        Matcher matcher = LOG_PATTERN.matcher(logLine);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }
}
