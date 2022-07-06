package com.example.dtt.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static com.example.dtt.constant.Constants.COMMON_PROPERTIES_PATH;

public class PropertyUtils {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtils.class);

    private static final Properties properties = new Properties();

    private PropertyUtils() {
        throw new UnsupportedOperationException("Construct PropertyUtils");
    }

    static {
        loadPropertyFile(COMMON_PROPERTIES_PATH);
    }

    public static synchronized void loadPropertyFile(String... propertyFiles) {
        for (String fileName : propertyFiles) {
            try (InputStream fis = PropertyUtils.class.getResourceAsStream(fileName);) {
                properties.load(fis);

            } catch (IOException e) {
                logger.error(e.getMessage(), e);
                System.exit(1);
            }
        }

        // Override from system properties
        System.getProperties().forEach((k, v) -> {
            final String key = String.valueOf(k);
            logger.info("Overriding property from system property: {}", key);
            PropertyUtils.setValue(key, String.valueOf(v));
        });
    }

    /**
     * get all properties with specified prefix, like: fs.
     *
     * @param prefix prefix to search
     * @return all properties with specified prefix
     */
    public static Map<String, String> getPrefixedProperties(String prefix) {
        Map<String, String> matchedProperties = new HashMap<>();
        for (String propName : properties.stringPropertyNames()) {
            if (propName.startsWith(prefix)) {
                matchedProperties.put(propName, properties.getProperty(propName));
            }
        }
        return matchedProperties;
    }
    /**
     * get property value
     *
     * @param key property name
     * @return property value
     */
    public static String getString(String key) {
        return properties.getProperty(key.trim());
    }

    /**
     * get property value with upper case
     *
     * @param key property name
     * @return property value  with upper case
     */
    public static String getUpperCaseString(String key) {
        String val = getString(key);
        return StringUtils.isEmpty(val) ? val : val.toUpperCase();
    }

    /**
     * get property value
     *
     * @param key property name
     * @param defaultVal default value
     * @return property value
     */
    public static String getString(String key, String defaultVal) {
        String val = getString(key);
        return StringUtils.isEmpty(val) ? defaultVal : val;
    }

    /**
     * get property value
     *
     * @param key property name
     * @return get property int value , if key == null, then return -1
     */
    public static int getInt(String key) {
        return getInt(key, -1);
    }

    /**
     * @param key key
     * @param defaultValue default value
     * @return property value
     */
    public static int getInt(String key, int defaultValue) {
        String value = getString(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            logger.info(e.getMessage(), e);
        }
        return defaultValue;
    }

    /**
     * get property value
     *
     * @param key property name
     * @return property value
     */
    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    /**
     * get property value
     *
     * @param key property name
     * @param defaultValue default value
     * @return property value
     */
    public static Boolean getBoolean(String key, boolean defaultValue) {
        String value = getString(key);
        return StringUtils.isEmpty(value) ? defaultValue : Boolean.parseBoolean(value);
    }

    /**
     * get property long value
     *
     * @param key key
     * @param defaultValue default value
     * @return property value
     */
    public static long getLong(String key, long defaultValue) {
        String value = getString(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            logger.info(e.getMessage(), e);
        }
        return defaultValue;
    }

    /**
     * @param key key
     * @return property value
     */
    public static long getLong(String key) {
        return getLong(key, -1);
    }

    /**
     * set value
     * @param key key
     * @param value value
     */
    public static void setValue(String key, String value) {
        properties.setProperty(key, value);
    }

}
