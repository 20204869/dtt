package com.example.dtt.utils.hadoop;

import com.example.dtt.constant.Constants;
import com.example.dtt.constant.file.FileConstants;
import com.example.dtt.utils.DateUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;

import static com.example.dtt.constant.file.FileConstants.FOLDER_SEPARATOR;
import static com.example.dtt.constant.file.FileConstants.UTF_8;

/**
 * file utils
 */
public class FileUtils {

    public static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    public static final String DATA_BASEDIR = FileConstants.DATA_BASEDIR_PATH;

    private FileUtils() {
        throw new UnsupportedOperationException("Construct FileUtils");
    }

    /**
     * get download file absolute path and name
     *
     * @param filename file name
     * @return download file name
     */
    public static String getDownloadFilename(String filename) {
        String fileName = String.format("%s/download/%s/%s", DATA_BASEDIR, DateUtils.getCurrentTime(Constants.YYYYMMDDHHMMSS), filename);

        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        return fileName;
    }

    /**
     * get upload file absolute path and name
     *
     * @param tenantCode tenant code
     * @param filename file name
     * @return local file path
     */
    public static String getUploadFilename(String tenantCode, String filename) {
        String fileName = String.format("%s/%s/resources/%s", DATA_BASEDIR, tenantCode, filename);
        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        return fileName;
    }

    /**
     * directory of process execution
     *
     * @param projectCode project code
     * @param processDefineCode process definition Code
     * @param processDefineVersion process definition version
     * @param processInstanceId process instance id
     * @param taskInstanceId task instance id
     * @return directory of process execution
     */
    public static String getProcessExecDir(long projectCode, long processDefineCode, int processDefineVersion, int processInstanceId, int taskInstanceId) {
        String fileName = String.format("%s/exec/process/%d/%s/%d/%d", DATA_BASEDIR,
                projectCode, processDefineCode + "_" + processDefineVersion, processInstanceId, taskInstanceId);
        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        return fileName;
    }

    /**
     * 在线预览文件支持的格式
     * @return get suffixes for resource files that support online viewing
     */
    public static String getResourceViewSuffixes() {
        return FileConstants.RESOURCE_VIEW_SUFFIXES_DEFAULT_VALUE;
    }

    /**
     * create directory if absent
     *
     * @param execLocalPath execute local path
     * @throws IOException errors
     */
    public static void createWorkDirIfAbsent(String execLocalPath) throws IOException {
        //if work dir exists, first delete
        File execLocalPathFile = new File(execLocalPath);

        if (execLocalPathFile.exists()) {
            org.apache.commons.io.FileUtils.forceDelete(execLocalPathFile);
        }

        //create work dir
        org.apache.commons.io.FileUtils.forceMkdir(execLocalPathFile);
        String mkdirLog = "create dir success " + execLocalPath;
        logger.info(mkdirLog);
    }

    /**
     * write content to file ,if parent path not exists, it will do one's utmost to mkdir
     *
     * @param content content
     * @param filePath target file path
     * @return true if write success
     */
    public static boolean writeContent2File(String content, String filePath) {
        FileOutputStream fos = null;
        try {
            File distFile = new File(filePath);
            if (!distFile.getParentFile().exists() && !distFile.getParentFile().mkdirs()) {
                logger.error("mkdir parent failed");
                return false;
            }
            fos = new FileOutputStream(filePath);
            IOUtils.write(content, fos, StandardCharsets.UTF_8);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        } finally {
            IOUtils.closeQuietly(fos);
        }
        return true;
    }

    /**
     * Deletes a file. If file is a directory, delete it and all sub-directories.
     * <p>
     * The difference between File.delete() and this method are:
     * <ul>
     * <li>A directory to be deleted does not have to be empty.</li>
     * <li>You get exceptions when a file or directory cannot be deleted.
     *      (java.io.File methods returns a boolean)</li>
     * </ul>
     *
     * @param filename file name
     */
    public static void deleteFile(String filename) {
        org.apache.commons.io.FileUtils.deleteQuietly(new File(filename));
    }

    /**
     * Gets all the parent subdirectories of the parentDir directory
     *
     * @param parentDir parent dir
     * @return all dirs
     */
    public static File[] getAllDir(String parentDir) {
        if (parentDir == null || "".equals(parentDir)) {
            throw new RuntimeException("parentDir can not be empty");
        }

        File file = new File(parentDir);
        if (!file.exists() || !file.isDirectory()) {
            throw new RuntimeException("parentDir not exist, or is not a directory:" + parentDir);
        }

        return file.listFiles(File::isDirectory);
    }

    /**
     * Get Content
     *
     * @param inputStream input stream
     * @return string of input stream
     */
    public static String readFile2Str(InputStream inputStream) {

        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, length);
            }
            return output.toString(UTF_8);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断文件名是否可遍历
     * @param filename String type of filename
     * @return whether file path could be traversal or not
     */
    public static boolean directoryTraversal(String filename){
        if (filename.contains(FOLDER_SEPARATOR)) {
            return true;
        }
        File file = new File(filename);
        try {
            File canonical = file.getCanonicalFile();
            File absolute = file.getAbsoluteFile();
            return !canonical.equals(absolute);
        } catch (IOException e) {
            return true;
        }
    }

}
