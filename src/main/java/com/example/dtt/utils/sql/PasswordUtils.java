package com.example.dtt.utils.sql;

import com.example.dtt.constant.datasource.Constants;
import com.example.dtt.utils.PropertyUtils;
import com.example.dtt.utils.StringUtils;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;

public class PasswordUtils {

    private static final Logger logger = LoggerFactory.getLogger(PasswordUtils.class);

    private static final Base64 BASE64 = new Base64();

    private PasswordUtils() {
        throw new UnsupportedOperationException("Construct PasswordUtils");
    }

    /**
     * encode password
     */
    public static String encodePassword(String password) {
        if (StringUtils.isEmpty(password)) {
            return StringUtils.EMPTY;
        }
        //if encryption is not turned on, return directly
        boolean encryptionEnable = PropertyUtils.getBoolean(Constants.DATASOURCE_ENCRYPTION_ENABLE, false);
        if (!encryptionEnable) {
            return password;
        }

        // Using Base64 + salt to process password
        String salt = PropertyUtils.getString(Constants.DATASOURCE_ENCRYPTION_SALT, Constants.DATASOURCE_ENCRYPTION_SALT_DEFAULT);
        String passwordWithSalt = salt + new String(BASE64.encode(password.getBytes(
            StandardCharsets.UTF_8)));
        return new String(BASE64.encode(passwordWithSalt.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * decode password
     */
    public static String decodePassword(String password) {
        if (StringUtils.isEmpty(password)) {
            return StringUtils.EMPTY;
        }

        //if encryption is not turned on, return directly
        boolean encryptionEnable = PropertyUtils.getBoolean(Constants.DATASOURCE_ENCRYPTION_ENABLE, false);
        if (!encryptionEnable) {
            return password;
        }

        // Using Base64 + salt to process password
        String salt = PropertyUtils.getString(Constants.DATASOURCE_ENCRYPTION_SALT, Constants.DATASOURCE_ENCRYPTION_SALT_DEFAULT);
        String passwordWithSalt = new String(BASE64.decode(password), StandardCharsets.UTF_8);
        if (!passwordWithSalt.startsWith(salt)) {
            logger.warn("There is a password and salt mismatch: {} ", password);
            return password;
        }
        return new String(BASE64.decode(passwordWithSalt.substring(salt.length())), StandardCharsets.UTF_8);
    }

}
