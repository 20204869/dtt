package com.example.dtt.utils.hadoop;

import com.example.dtt.constant.file.FileConstants;
import com.example.dtt.constant.task.TaskConstants;
import com.example.dtt.enums.TaskStatus;
import com.example.dtt.exception.base.BaseException;
import com.example.dtt.service.file.StorageOperate;
import com.example.dtt.utils.JSONUtils;
import com.example.dtt.utils.PropertyUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.security.PrivilegedExceptionAction;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * hadoop utils
 * single instance
 */
public class HadoopUtils implements Closeable, StorageOperate {

    private static final Logger logger = LoggerFactory.getLogger(HadoopUtils.class);
    private String hdfsUser = FileConstants.HDFS_ROOT_USER;
    public static final String RM_HA_IDS = FileConstants.YARN_RESOURCEMANAGER_HA_RM_IDS;
    public static final String APP_ADDRESS = FileConstants.YARN_APPLICATION_STATUS_ADDRESS;
    public static final String JOB_HISTORY_ADDRESS = FileConstants.YARN_JOB_HISTORY_STATUS_ADDRESS;
    public static final int HADOOP_RESOURCE_MANAGER_HTTP_ADDRESS_PORT_VALUE = FileConstants.HADOOP_RESOURCE_MANAGER_HTTPADDRESS_PORT;
    private static final String HADOOP_UTILS_KEY = "HADOOP_UTILS_KEY";

    private static final LoadingCache<String, HadoopUtils> cache = CacheBuilder
            .newBuilder()
            .expireAfterWrite(2, TimeUnit.HOURS)
            .build(new CacheLoader<String, HadoopUtils>() {
                @Override
                public HadoopUtils load(String key) throws Exception {
                    return new HadoopUtils();
                }
            });

    private volatile boolean yarnEnabled = false;

    private Configuration configuration;
    private FileSystem fs;

    private HadoopUtils() {
        hdfsUser = FileConstants.HDFS_ROOT_USER;
        init();
        initHdfsPath();
    }

    public static HadoopUtils getInstance() {
        return cache.getUnchecked(HADOOP_UTILS_KEY);
    }

    /**
     * 文件管理在hdfs上的初始目录
     */
    private void initHdfsPath() {
        Path path = new Path(FileConstants.FILE_UPLOAD_PATH);
        try {
            if (!fs.exists(path)) {
                fs.mkdirs(path);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * init hadoop configuration
     */
    private void init() throws NullPointerException {
        try {
            configuration = new HdfsConfiguration();
            String defaultFS = configuration.get(FileConstants.FS_DEFAULT_FS);
            //first get key from core-site.xml hdfs-site.xml ,if null ,then try to get from properties file
            // the default is the local file system
            if (defaultFS.startsWith("file")) {
                String defaultFSProp = FileConstants.FS_DEFAULT_FS;
                if (StringUtils.isNotBlank(defaultFSProp)) {
                    Map<String, String> fsRelatedProps = PropertyUtils.getPrefixedProperties("fs.");
                    configuration.set(FileConstants.FS_DEFAULT_FS, defaultFSProp);
                    fsRelatedProps.forEach((key, value) -> configuration.set(key, value));
                } else {
                    logger.error("property:{} can not to be empty, please set!", FileConstants.FS_DEFAULT_FS);
                    throw new NullPointerException(
                            String.format("property: %s can not to be empty, please set!", FileConstants.FS_DEFAULT_FS)
                    );
                }
            } else {
                logger.info("get property:{} -> {}, from core-site.xml hdfs-site.xml ", FileConstants.FS_DEFAULT_FS, defaultFS);
            }
            if (StringUtils.isNotEmpty(hdfsUser)) {
                UserGroupInformation ugi = UserGroupInformation.createRemoteUser(hdfsUser);
                ugi.doAs((PrivilegedExceptionAction<Boolean>) () -> {
                    fs = FileSystem.get(configuration);
                    return true;
                });
            } else {
                logger.warn("hdfs.root.user is not set value!");
                fs = FileSystem.get(configuration);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * @return Configuration
     */
    public Configuration getConfiguration() {
        return configuration;
    }

    /**
     * @return DefaultFS
     */
    public String getDefaultFS() {
        return getConfiguration().get(FileConstants.FS_DEFAULT_FS);
    }

    /**
     * get application url
     * if rmHaIds contains xx, it signs not use resourcemanager
     * otherwise:
     * if rmHaIds is empty, single resourcemanager enabled
     * if rmHaIds not empty: resourcemanager HA enabled
     *
     * @param applicationId application id
     * @return url of application

    public String getApplicationUrl(String applicationId) throws BaseException {
        yarnEnabled = true;
        String appUrl = StringUtils.isEmpty(RM_HA_IDS) ? APP_ADDRESS : getAppAddress(APP_ADDRESS, RM_HA_IDS);
        if (StringUtils.isBlank(appUrl)) {
            throw new BaseException("yarn application url generation failed");
        }
        if (logger.isDebugEnabled()) {
            logger.debug("yarn application url:{}, applicationId:{}", appUrl, applicationId);
        }
        return String.format(appUrl, HADOOP_RESOURCE_MANAGER_HTTP_ADDRESS_PORT_VALUE, applicationId);
    }
     */
    public String getJobHistoryUrl(String applicationId) {
        //eg:application_1587475402360_712719 -> job_1587475402360_712719
        String jobId = applicationId.replace("application", "job");
        return String.format(JOB_HISTORY_ADDRESS, jobId);
    }

    /**
     * cat file on hdfs
     *
     * @param hdfsFilePath hdfs file path
     * @return byte[] byte array
     * @throws IOException errors
     */
    public byte[] catFile(String hdfsFilePath) throws IOException {
        if (StringUtils.isBlank(hdfsFilePath)) {
            logger.error("hdfs file path:{} is blank", hdfsFilePath);
            return new byte[0];
        }
        try (FSDataInputStream fsDataInputStream = fs.open(new Path(hdfsFilePath))) {
            return IOUtils.toByteArray(fsDataInputStream);
        }
    }
    /**
     * cat file on hdfs
     *
     * @param hdfsFilePath hdfs file path
     * @param skipLineNums skip line numbers
     * @param limit        read how many lines
     * @return content of file
     * @throws IOException errors
     */
    public List<String> catFile(String hdfsFilePath, int skipLineNums, int limit) throws IOException {
        if (StringUtils.isBlank(hdfsFilePath)) {
            logger.error("hdfs file path:{} is blank", hdfsFilePath);
            return Collections.emptyList();
        }
        try (FSDataInputStream in = fs.open(new Path(hdfsFilePath))) {
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
            Stream<String> stream = br.lines().skip(skipLineNums).limit(limit);
            return stream.collect(Collectors.toList());
        }
    }

    @Override
    public List<String> vimFile(String hdfsFilePath, int skipLineNums, int limit) throws IOException {
        return catFile(hdfsFilePath, skipLineNums, limit);
    }

    //按用户创建目录
    public void createDirIfNotExists(String userName) throws IOException {
        getInstance().mkdir(getHdfsDir(userName));
    }

    public static String getHdfsDir(String userName) {
        return String.format(FileConstants.FORMAT_S_S, getHdfsDataBasePath(), userName);
    }

    /**
     * get data hdfs path
     * @return data hdfs path
     */
    public static String getHdfsDataBasePath() {
        if (FileConstants.FOLDER_SEPARATOR.equals(FileConstants.FILE_UPLOAD_PATH)) {
            return "";
        } else {
            return FileConstants.FILE_UPLOAD_PATH;
        }
    }

    //下载文件
    @Override
    public void download(String srcHdfsFilePath, String dstFile, boolean deleteSource, boolean overwrite) throws IOException {
        copyHdfsToLocal(srcHdfsFilePath, dstFile, deleteSource, overwrite);
    }

    /**
     * make the given file and all non-existent parents into
     * directories. Has the semantics of Unix 'mkdir -p'.
     * Existence of the directory hierarchy is not an error.
     *
     * @param hdfsPath path to create
     * @return mkdir result
     * @throws IOException errors
     */
    @Override
    public boolean mkdir(String hdfsPath) throws IOException {
        return fs.mkdirs(new Path(hdfsPath));
    }

    @Override
    public String getResourceFileName(String tenantCode, String fullName) {
        return getHdfsResourceFileName(tenantCode, fullName);
    }

    /**
     * get absolute path and name for resource file on hdfs
     * @param userName userName
     * @param fileName   file name
     * @return get absolute path and name for file on hdfs
     */
    public static String getHdfsResourceFileName(String userName, String fileName) {
        if (fileName.startsWith(FileConstants.FOLDER_SEPARATOR)) {
            fileName = fileName.replaceFirst(FileConstants.FOLDER_SEPARATOR, "");
        }
        return String.format(FileConstants.FORMAT_S_S, getHdfsDir(userName), fileName);
    }

    @Override
    public String getFileName(String tenantCode, String fileName) {
        return getHdfsFileName(tenantCode, fileName);
    }

    /**
     * copy files between FileSystems
     *
     * @param srcPath      source hdfs path
     * @param dstPath      destination hdfs path
     * @param deleteSource whether to delete the src
     * @param overwrite    whether to overwrite an existing file
     * @return if success or not
     * @throws IOException errors
     */
    @Override
    public boolean copy(String srcPath, String dstPath, boolean deleteSource, boolean overwrite) throws IOException {
        return FileUtil.copy(fs, new Path(srcPath), fs, new Path(dstPath), deleteSource, overwrite, fs.getConf());
    }

    /**
     * the src file is on the local disk.  Add it to FS at
     * the given dst name.
     *
     * @param srcFile      local file
     * @param dstHdfsPath  destination hdfs path
     * @param deleteSource whether to delete the src
     * @param overwrite    whether to overwrite an existing file
     * @return if success or not
     * @throws IOException errors
     */
    public boolean copyLocalToHdfs(String srcFile, String dstHdfsPath, boolean deleteSource, boolean overwrite) throws IOException {
        Path srcPath = new Path(srcFile);
        Path dstPath = new Path(dstHdfsPath);
        fs.copyFromLocalFile(deleteSource, overwrite, srcPath, dstPath);
        return true;
    }

    @Override
    public boolean upload(String srcFile, String dstPath, boolean deleteSource, boolean overwrite) throws IOException {
        return copyLocalToHdfs(srcFile, dstPath, deleteSource, overwrite);
    }

    /*
     * copy hdfs file to local
     *
     * @param srcHdfsFilePath source hdfs file path
     * @param dstFile         destination file
     * @param deleteSource    delete source
     * @param overwrite       overwrite
     * @return result of copy hdfs file to local
     * @throws IOException errors
     */
    public boolean copyHdfsToLocal(String srcHdfsFilePath, String dstFile, boolean deleteSource, boolean overwrite) throws IOException {
        Path srcPath = new Path(srcHdfsFilePath);
        File dstPath = new File(dstFile);
        if (dstPath.exists()) {
            if (dstPath.isFile()) {
                if (overwrite) {
                    Files.delete(dstPath.toPath());
                }
            } else {
                logger.error("destination file must be a file");
            }
        }

        if (!dstPath.getParentFile().exists() && !dstPath.getParentFile().mkdirs()) {
            return false;
        }

        return FileUtil.copy(fs, srcPath, dstPath, deleteSource, fs.getConf());
    }

    /**
     * delete a file
     *
     * @param hdfsFilePath the path to delete.
     * @param recursive    if path is a directory and set to
     *                     true, the directory is deleted else throws an exception. In
     *                     case of a file the recursive can be set to either true or false.
     * @return true if delete is successful else false.
     * @throws IOException errors
     */
    @Override
    public boolean delete(String hdfsFilePath, boolean recursive) throws IOException {
        return fs.delete(new Path(hdfsFilePath), recursive);
    }

    /**
     * check if exists
     * @param hdfsFilePath source file path
     * @return result of exists or not
     * @throws IOException errors
     */
    @Override
    public boolean exists(String hdfsFilePath) throws IOException {
        return fs.exists(new Path(hdfsFilePath));
    }

    /**
     * Gets a list of files in the directory
     *
     * @param filePath file path
     * @return {@link FileStatus} file status
     * @throws IOException errors
     */
    public FileStatus[] listFileStatus(String filePath) throws IOException {
        try {
            return fs.listStatus(new Path(filePath));
        } catch (IOException e) {
            logger.error("Get file list exception", e);
            throw new IOException("Get file list exception", e);
        }
    }

    /**
     * Renames Path src to Path dst.  Can take place on local fs
     * or remote DFS.
     *
     * @param src path to be renamed
     * @param dst new path after rename
     * @return true if rename is successful
     * @throws IOException on failure
     */
    public boolean rename(String src, String dst) throws IOException {
        return fs.rename(new Path(src), new Path(dst));
    }

    /**
     * hadoop resourcemanager enabled or not
     *
     * @return result
     */
    public boolean isYarnEnabled() {
        return yarnEnabled;
    }

    /**
     * get the state of an application
     *
     * @param applicationId application id
     * @return the return may be null or there may be other parse exceptions

    public TaskStatus getApplicationStatus(String applicationId) throws BaseException {
        if (StringUtils.isEmpty(applicationId)) {
            return null;
        }

        String result;
        String applicationUrl = getApplicationUrl(applicationId);
        if (logger.isDebugEnabled()) {
            logger.debug("generate yarn application url, applicationUrl={}", applicationUrl);
        }

        String responseContent = Boolean.TRUE.equals(PropertyUtils.getBoolean(FileConstants.HADOOP_SECURITY_AUTHENTICATION_STARTUP_STATE, false)) ? KerberosHttpClient.get(applicationUrl) : HttpUtils.get(applicationUrl);
        if (responseContent != null) {
            ObjectNode jsonObject = JSONUtils.parseObject(responseContent);
            if (!jsonObject.has("app")) {
                return TaskStatus.FAILURE;
            }
            result = jsonObject.path("app").path("finalStatus").asText();

        } else {
            //may be in job history
            String jobHistoryUrl = getJobHistoryUrl(applicationId);
            if (logger.isDebugEnabled()) {
                logger.debug("generate yarn job history application url, jobHistoryUrl={}", jobHistoryUrl);
            }
            responseContent = Boolean.TRUE.equals(PropertyUtils.getBoolean(FileConstants.HADOOP_SECURITY_AUTHENTICATION_STARTUP_STATE, false)) ? KerberosHttpClient.get(jobHistoryUrl) : HttpUtils.get(jobHistoryUrl);

            if (null != responseContent) {
                ObjectNode jsonObject = JSONUtils.parseObject(responseContent);
                if (!jsonObject.has("job")) {
                    return TaskStatus.FAILURE;
                }
                result = jsonObject.path("job").path("state").asText();
            } else {
                return TaskStatus.FAILURE;
            }
        }

        return getExecutionStatus(result);
    } */

    private TaskStatus getExecutionStatus(String result) {
        switch (result) {
            case TaskConstants.ACCEPTED:
                return TaskStatus.SUBMITTED_SUCCESS;
            case TaskConstants.SUCCEEDED:
            case TaskConstants.ENDED:
                return TaskStatus.SUCCESS;
            case TaskConstants.NEW:
            case TaskConstants.NEW_SAVING:
            case TaskConstants.SUBMITTED:
            case TaskConstants.FAILED:
                return TaskStatus.FAILURE;
            case TaskConstants.KILLED:
                return TaskStatus.KILL;
            case TaskConstants.RUNNING:
            default:
                return TaskStatus.RUNNING_EXECUTION;
        }
    }

    @Override
    public String getDir(String tenantCode) {
        return getHdfsDir(tenantCode);
    }

    /**
     * get hdfs file name
     * @param userName   userName
     * @param fileName     file name
     * @return hdfs file name
     */
    public static String getHdfsFileName(String userName, String fileName) {
        if (fileName.startsWith(FileConstants.FOLDER_SEPARATOR)) {
            fileName = fileName.replaceFirst(FileConstants.FOLDER_SEPARATOR, "");
        }
        return String.format(FileConstants.FORMAT_S_S, getHdfsDir(userName), fileName);
    }

    /**
     * getAppAddress
     *
     * @param appAddress app address
     * @param rmHa       resource manager ha
     * @return app address

    public static String getAppAddress(String appAddress, String rmHa) {

        //get active ResourceManager
        String activeRM = YarnHAAdminUtils.getActiveRMName(rmHa);

        if (StringUtils.isEmpty(activeRM)) {
            return null;
        }

        String[] split1 = appAddress.split(FileConstants.DOUBLE_SLASH);

        if (split1.length != 2) {
            return null;
        }

        String start = split1[0] + FileConstants.DOUBLE_SLASH;
        String[] split2 = split1[1].split(FileConstants.COLON);

        if (split2.length != 2) {
            return null;
        }

        String end = FileConstants.COLON + split2[1];

        return start + activeRM + end;
    }*/

    @Override
    public void close() throws IOException {
        if (fs != null) {
            try {
                fs.close();
            } catch (IOException e) {
                logger.error("Close HadoopUtils instance failed", e);
                throw new IOException("Close HadoopUtils instance failed", e);
            }
        }
    }

    /**
     * yarn ha admin utils
     */
    private static final class YarnHAAdminUtils {

        /**
         * get active resourcemanager

        public static String getActiveRMName(String rmIds) {

            String[] rmIdArr = rmIds.split(FileConstants.COMMA);

            String yarnUrl = "http://%s:" + HADOOP_RESOURCE_MANAGER_HTTP_ADDRESS_PORT_VALUE + "/ws/v1/cluster/info";

            try {
                for (String rmId : rmIdArr) {
                    String state = getRMState(String.format(yarnUrl, rmId));
                    if (FileConstants.HADOOP_RM_STATE_ACTIVE.equals(state)) {
                        return rmId;
                    }
                }

            } catch (Exception e) {
                logger.error("yarn ha application url generation failed, message:{}", e.getMessage());
            }
            return null;
        }*/

        /**
         * get ResourceManager state

        public static String getRMState(String url) {

            String retStr = Boolean.TRUE.equals(PropertyUtils.getBoolean(FileConstants.HADOOP_SECURITY_AUTHENTICATION_STARTUP_STATE, false)) ? KerberosHttpClient.get(url) : HttpUtils.get(url);

            if (StringUtils.isEmpty(retStr)) {
                return null;
            }
            //to json
            ObjectNode jsonObject = JSONUtils.parseObject(retStr);

            //get ResourceManager state
            if (!jsonObject.has("clusterInfo")) {
                return null;
            }
            return jsonObject.get("clusterInfo").path("haState").asText();
        }
         */
    }

    @Override
    public void deleteUserName(String userName) throws Exception {
        String path = getHdfsDataBasePath() + FileConstants.FOLDER_SEPARATOR + userName;
        if (exists(path)) {
            delete(path, true);

        }
    }

}
