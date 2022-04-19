package com.example.dtt.utils.sql;

import com.example.dtt.constant.datasource.Constants;
import com.example.dtt.enums.ResUploadType;
import com.example.dtt.utils.PropertyUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;


public class CommonUtil {

    private CommonUtil() {
    }

    public static boolean getKerberosStartupState() {
        String resUploadStartupType = PropertyUtils.getUpperCaseString(Constants.RESOURCE_STORAGE_TYPE);
        ResUploadType resUploadType = ResUploadType.valueOf(resUploadStartupType);
        Boolean kerberosStartupState = PropertyUtils.getBoolean(Constants.HADOOP_SECURITY_AUTHENTICATION_STARTUP_STATE, false);
        return resUploadType == ResUploadType.HDFS && kerberosStartupState;
    }

    public static synchronized UserGroupInformation createUGI(Configuration configuration, String username)
            throws IOException {
        return UserGroupInformation.createRemoteUser(username);
    }
}
