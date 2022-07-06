package com.example.dtt.utils;

import java.util.regex.Pattern;

public class RegexUtils {

    private static final String LINUX_USERNAME_PATTERN = "^[a-zA-Z0-9_].{0,30}";

    private RegexUtils() {
    }

    /**
     * check if the input is a valid linux username
     * @param str input
     * @return boolean
     */
    public static boolean isValidLinuxUserName(String str) {
        Pattern pattern = Pattern.compile(LINUX_USERNAME_PATTERN);
        return pattern.matcher(str).matches();
    }

    public static String escapeNRT(String str) {
        // Logging should not be vulnerable to injection attacks: Replace pattern-breaking characters
        if (!StringUtils.isEmpty(str)) {
            return str.replaceAll("[\n|\r|\t]", "_");
        }
        return null;
    }

}
