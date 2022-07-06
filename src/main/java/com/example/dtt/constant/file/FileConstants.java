package com.example.dtt.constant.file;

/**
 * @author reid
 * @date 2022/7/4 13:23
 * @describe
 */
public class FileConstants {

    public static final String FOLDER_SEPARATOR = "/";
    public static final String FORMAT_SS = "%s%s";
    public static final String FORMAT_S_S = "%s/%s";

    public static final String DATA_BASEDIR_PATH = "/tmp/dttfile";
    public static final String FILE_UPLOAD_PATH = "/dttfile";
    public static final String HDFS_ROOT_USER = "hdfs";
    public static final String FS_DEFAULT_FS = "hdfs://nameservice1";


    //file size
    public static final int MAX_FILE_SIZE = 1024 * 1024 * 1024;
    public static final int FILE_FULL_NAME_MAX_LENGTH = 128;
    /**
     * DOUBLE_SLASH //
     */
    public static final String DOUBLE_SLASH = "//";
    /**
     * COLON :
     */
    public static final String COLON = ":";
    /**
     * comma ,
     */
    public static final String COMMA = ",";

    /**
     * hadoop configuration
     */
    public static final String HADOOP_RM_STATE_ACTIVE = "ACTIVE";
    /**
     * resource.view.suffixs
     */
    public static final String RESOURCE_VIEW_SUFFIXES = "resource.view.suffixs";

    public static final String RESOURCE_VIEW_SUFFIXES_DEFAULT_VALUE = "txt,log,sh,py,java,sql,xml,properties,json,js";
    /**
     * yarn.resourcemanager.ha.rm.ids
     */
    public static final String YARN_RESOURCEMANAGER_HA_RM_IDS = "172.20.70.159,172.20.70.158";
    /**
     * yarn.application.status.address
     */
    public static final String YARN_APPLICATION_STATUS_ADDRESS = "http://xxx:8088/ws/v1/cluster/apps/%s";
    /**
     * yarn.job.history.status.address
     */
    public static final String YARN_JOB_HISTORY_STATUS_ADDRESS = "http://xxx:19888/ws/v1/history/mapreduce/jobs/%s";
    public static final int HADOOP_RESOURCE_MANAGER_HTTPADDRESS_PORT = 8088;


    /**
     * UTF-8
     */
    public static final String UTF_8 = "UTF-8";

    /**
     * hadoop.security.authentication
     */
    public static final String HADOOP_SECURITY_AUTHENTICATION_STARTUP_STATE = "false";
    /**
     * message
     */
    public static final String MSG = "msg";
    public static final String STATUS = "status";

    public static final String DATA_LIST = "data";

    public static final String TOTAL_LIST = "totalList";

    public static final String CURRENT_PAGE = "currentPage";

    public static final String TOTAL_PAGE = "totalPage";

    public static final String TOTAL = "total";
}
