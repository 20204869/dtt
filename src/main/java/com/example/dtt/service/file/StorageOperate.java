package com.example.dtt.service.file;

import java.io.IOException;
import java.util.List;


public interface StorageOperate {
    /**
     * if the resource of userName 's exist, the resource of folder will be created
     * @param userName
     * @throws Exception
     */
     void createDirIfNotExists(String userName) throws Exception;

    /**
     * create the directory that the path of userName wanted to create
     * @param path
     * @return
     * @throws IOException
     */
     boolean mkdir(String path) throws IOException;

    /**
     * get the path of the resource file
     * @param userName
     * @param fullName
     * @return
     */
     String getResourceFileName(String userName, String fullName);

    /**
     * get the path of the file
     * @param userName
     * @param fileName
     * @return
     */
     String getFileName(String userName, String fileName);

    /**
     * predicate  if the resource of tenant exists
     * @param fileName
     * @return
     * @throws IOException
     */
      boolean exists(String fileName) throws IOException;

    /**
     * delete the resource of  filePath
     * @param filePath
     * @param recursive
     * @return
     * @throws IOException
     */
     boolean delete(String filePath, boolean recursive) throws IOException;

    /**
     * copy the file from srcPath to dstPath
     * @param srcPath
     * @param dstPath
     * @param deleteSource if need to delete the file of srcPath
     * @param overwrite
     * @return
     * @throws IOException
     */
     boolean copy(String srcPath, String dstPath, boolean deleteSource, boolean overwrite) throws IOException;

    /**
     * 初始目录
     * @return
     */
      String getDir(String userName);

    /**
     * upload the local srcFile to dstPath
     * @param srcFile
     * @param dstPath
     * @param deleteSource
     * @param overwrite
     * @return
     * @throws IOException
     */
     boolean upload(String srcFile, String dstPath, boolean deleteSource, boolean overwrite) throws IOException;

    /**
     * download the srcPath to local
     * @param srcFilePath the full path of the srcPath
     * @param dstFile
     * @param deleteSource
     * @param overwrite
     * @throws IOException
     */
     void download(String srcFilePath, String dstFile, boolean deleteSource, boolean overwrite)throws IOException;

    /**
     * vim the context of filePath
     * @param filePath
     * @param skipLineNums
     * @param limit
     * @return
     * @throws IOException
     */
     List<String> vimFile(String filePath, int skipLineNums, int limit) throws IOException;

    /**
     * delete the files and directory of the userName
     * @throws Exception
     */
     void deleteUserName(String userName) throws Exception;

}
