package com.example.dtt.utils.sql;

import com.example.dtt.enums.ResUploadType;
import com.example.dtt.utils.PropertyUtils;
import com.example.dtt.utils.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;

import java.io.IOException;

import static com.example.dtt.constant.TaskConstants.*;

/**
 * common utils
 */
public class CommonUtils {

    private CommonUtils() {
        throw new UnsupportedOperationException("Construct CommonUtils");
    }

    /**
     * if upload resource is HDFS and kerberos startup is true , else false
     *
     * @return true if upload resource is HDFS and kerberos startup
     */
    public static boolean getKerberosStartupState() {
        String resUploadStartupType = PropertyUtils.getUpperCaseString(RESOURCE_STORAGE_TYPE);
        ResUploadType resUploadType = ResUploadType.valueOf(resUploadStartupType);
        Boolean kerberosStartupState = PropertyUtils.getBoolean(HADOOP_SECURITY_AUTHENTICATION_STARTUP_STATE, false);
        return resUploadType == ResUploadType.HDFS && kerberosStartupState;
    }

    public static String getDataQualityJarName() {
        String dqsJarName = PropertyUtils.getString(DATA_QUALITY_JAR_NAME);

        if (org.apache.commons.lang.StringUtils.isEmpty(dqsJarName)) {
            return "dolphinscheduler-data-quality.jar";
        }

        return dqsJarName;
    }

    /**
     * hdfs udf dir
     *
     * @param tenantCode tenant code
     * @return get udf dir on hdfs
     */
    public static String getHdfsUdfDir(String tenantCode) {
        return String.format("%s/udfs", getHdfsTenantDir(tenantCode));
    }

    /**
     * @param tenantCode tenant code
     * @return file directory of tenants on hdfs
     */
    public static String getHdfsTenantDir(String tenantCode) {
        return String.format("%s/%s", getHdfsDataBasePath(), tenantCode);
    }

    /**
     * get data hdfs path
     *
     * @return data hdfs path
     */
    public static String getHdfsDataBasePath() {
        String resourceUploadPath = PropertyUtils.getString(RESOURCE_UPLOAD_PATH, "/load");
        if ("/".equals(resourceUploadPath)) {
            // if basepath is configured to /,  the generated url may be  //default/resources (with extra leading /)
            return "";
        } else {
            return resourceUploadPath;
        }
    }
}
