package com.example.dtt.service.file;

import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.entity.file.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author reid
 * @date 2022/7/4 11:13
 * @describe
 */
public interface FileService {

    /**
     * 创建文件夹
     *
     * @param fileName
     * @param description
     * @param pid
     * @param currentDir
     * @return
     */
    AjaxResult createDirectory(String userName, String fileName, String description, int pid, String currentDir);

    /**
     * 根据条件筛选文件
     *
     * @param resource
     * @return
     */
    List<Resource> fileList(Resource resource);

    /**
     * 创建文件
     *
     * @param loginUser
     * @param name
     * @param desc
     * @param file
     * @param pid
     * @param currentDir
     * @return
     */
    AjaxResult createFile(String loginUser, String name, String desc, MultipartFile file, int pid, String currentDir);

    /**
     * 更新文件
     *
     * @param loginUser
     * @param fileId
     * @param name
     * @param desc
     * @param file
     * @return
     */
    AjaxResult updateFile(String loginUser, int fileId, String name, String desc, MultipartFile file);
/*
    AjaxResult delete(String loginUser, int resourceId);*/

    /**
     * 校验文件名
     *
     * @param fullName
     * @param loginUser
     * @return
     */
    AjaxResult verifyFileName(String fullName, String loginUser);


    AjaxResult queryFile(String loginUser, String fullName, Integer id);


    AjaxResult viewFile(String loginUser, int resourceId, int skipLineNum, int limit);


    AjaxResult onlineCreateFile(String loginUser, String fileName, String fileSuffix, String desc, String content, int pid, String currentDirectory);

    org.springframework.core.io.Resource downloadResource(String loginUser, int resourceId) throws IOException;

    AjaxResult updateFileContent(String loginUser,int resourceId, String content);

    AjaxResult queryFileById(String loginUser, Integer resourceId);
}
