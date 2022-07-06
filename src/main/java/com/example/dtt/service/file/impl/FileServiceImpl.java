package com.example.dtt.service.file.impl;

import com.example.dtt.constant.file.FileConstants;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.entity.file.Resource;
import com.example.dtt.exception.ServiceException;
import com.example.dtt.mapper.file.FileMapper;
import com.example.dtt.service.file.FileService;
import com.example.dtt.service.file.StorageOperate;
import com.example.dtt.utils.RegexUtils;
import com.example.dtt.utils.hadoop.FileUtils;
import com.example.dtt.utils.hadoop.HadoopUtils;
import com.google.common.base.Joiner;
import com.google.common.io.Files;
import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.rmi.ServerException;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * @author reid
 * @date 2021/12/24 15:28
 * @describe
 */
@Service
public class FileServiceImpl implements FileService {
    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private FileMapper fileMapper;

    @Autowired(required = false)
    private StorageOperate storageOperate;


    @Override
    public List<Resource> fileList(Resource resource) {
        return fileMapper.fileList(resource);
    }

    @Override
    public AjaxResult queryFileById(String loginUser, Integer resourceId) {
        AjaxResult result = AjaxResult.success();
        Resource resource = fileMapper.selectById(resourceId);
        if (resource == null) {
            return AjaxResult.error("文件不存在");
        }
        result.put("data",resource);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult updateFileContent(String loginUser, int resourceId, String content) {
        Resource resource = fileMapper.selectById(resourceId);
        if (resource == null) {
            logger.error("read file not exist,  resource id {}", resourceId);
            return AjaxResult.error("更新的文件不存在");
        }

        //check can edit by file suffix
        String nameSuffix = Files.getFileExtension(resource.getFileName());
        String resourceViewSuffixes = FileUtils.getResourceViewSuffixes();
        if (StringUtils.isNotEmpty(resourceViewSuffixes)) {
            List<String> strList = Arrays.asList(resourceViewSuffixes.split(","));
            if (!strList.contains(nameSuffix)) {
                logger.error("resource suffix {} not support updateProcessInstance,  resource id {}", nameSuffix, resourceId);
                return AjaxResult.error("该文件前缀不支持在线编辑");
            }
        }


        long originFileSize = resource.getSize();
        resource.setSize(content.getBytes().length);
        resource.setUpdateTime(new Date());
        fileMapper.updateById(resource);

        AjaxResult result = uploadContentToStorage(loginUser, resource.getFullName(), content);
        updateParentResourceSize(resource, resource.getSize() - originFileSize);
        if (!result.isSuccess()) {
            throw new ServiceException("更新文件内容到hdfs出现异常");
        }
        return result;
    }

    @Override
    public org.springframework.core.io.Resource downloadResource(String loginUser, int resourceId) throws IOException{
        Resource resource = fileMapper.selectById(resourceId);
        if (resource == null) {
            logger.error("download file not exist,  resource id {}", resourceId);
            return null;
        }
        if (resource.isDirectory()) {
            logger.error("resource id {} is directory,can't download it", resourceId);
            throw new ServiceException("can't download directory");
        }
        String fileName = storageOperate.getFileName(loginUser, resource.getFullName());
        String localFileName = FileUtils.getDownloadFilename(resource.getFileName());
        logger.info("resource  path is {}, download local filename is {}", fileName, localFileName);
        try {
            storageOperate.download( fileName, localFileName, false, true);
            return com.example.dtt.utils.files.FileUtils.file2Resource(localFileName);
        } catch (IOException e) {
            logger.error("download resource error, the path is {}, and local filename is {}, the error message is {}", fileName, localFileName, e.getMessage());
            throw new ServerException("download the resource file failed ,it may be related to your storage");
        }
    }

    @Override
    public AjaxResult onlineCreateFile(String loginUser, String fileName, String fileSuffix, String desc, String content, int pid, String currentDirectory) {
        AjaxResult result = AjaxResult.success();
      /*  if (FileUtils.directoryTraversal(fileName)) {
            putMsg(result, Status.VERIFY_PARAMETER_NAME_FAILED);
            return result;
        }*/

        //check file suffix
        String nameSuffix = fileSuffix.trim();
        String resourceViewSuffixes = FileUtils.getResourceViewSuffixes();
        if (StringUtils.isNotEmpty(resourceViewSuffixes)) {
            List<String> strList = Arrays.asList(resourceViewSuffixes.split(","));
            if (!strList.contains(nameSuffix)) {
                logger.error("resource suffix {} not support create", nameSuffix);
                return AjaxResult.error("该文件前缀不支持预览");
            }
        }

        String name = fileName.trim() + "." + nameSuffix;
        String fullName = getFullName(currentDirectory, name);
        if (checkFileExists(fullName)) {
            logger.error("file directory {} has exist, can't recreate", fullName);
            return AjaxResult.error("文件名已存在请确认");
        }
        try {
            //hdfs路径下文件是否存在
            String hdfsFileName = storageOperate.getFileName(loginUser, fullName);
            if (storageOperate.exists(hdfsFileName)) {
                return AjaxResult.error("hdfs 路径下该文件已存在");
            }
        } catch (Exception e) {
            throw new ServiceException("访问hdfs异常");
        }
        //非父目录时，父目录不存在
        if (pid != -1) {
            Resource parentResource = fileMapper.selectByPId(pid);
            if (parentResource == null) {
                return AjaxResult.error("父目录不存在");
            }
        }

        // save data
        Date now = new Date();
        Resource resource = new Resource();
        resource.setRemark(desc);
        resource.setCreateBy(loginUser);
        resource.setFileName(name);
        resource.setFullName(fullName);
        resource.setDirectory(false);
        resource.setPid(pid);
        resource.setSize(content.getBytes().length);

        fileMapper.createDirectory(resource);
        updateParentResourceSize(resource, resource.getSize());
        result = uploadContentToStorage(loginUser, fullName, content);
        if (!result.isSuccess()) {
            throw new ServiceException("存储文件到hdfs上出现异常");
        }
        return result;
    }

    private AjaxResult uploadContentToStorage(String loginUser,String resourceName, String content) {
        AjaxResult result = AjaxResult.success();
        String localFilename = "";
        String storageFileName = "";
        try {
            localFilename = FileUtils.getUploadFilename(loginUser, UUID.randomUUID().toString());
            if (!FileUtils.writeContent2File(content, localFilename)) {
                // write file fail
                logger.error("file {} fail, content is {}", localFilename, RegexUtils.escapeNRT(content));
                return AjaxResult.error("文件不存在");
            }
            // get resource file  path
            storageFileName = storageOperate.getResourceFileName(loginUser, resourceName);
            String resourcePath = storageOperate.getDir(loginUser);
            logger.info("resource  path is {}, resource dir is {}", storageFileName, resourcePath);


            if (!storageOperate.exists(resourcePath)) {
                // create if tenant dir not exists
                storageOperate.createDirIfNotExists(loginUser);
            }
            if (storageOperate.exists(storageFileName)) {
                storageOperate.delete(storageFileName, false);
            }
            storageOperate.upload(localFilename, storageFileName, true, true);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return AjaxResult.error("操作hdfs出现异常"+String.format("copy %s to hdfs %s fail", localFilename, storageFileName));
        }
        return result;
    }

    @Override
    public AjaxResult viewFile(String loginUser, int resourceId, int skipLineNum, int limit) {
        AjaxResult result = AjaxResult.success();
        Resource resource = fileMapper.selectById(resourceId);
        if (resource == null) {
            return AjaxResult.error("文件不存在");
        }
        //check preview or not by file suffix
        String nameSuffix = Files.getFileExtension(resource.getFileName());
        String resourceViewSuffixes = FileUtils.getResourceViewSuffixes();
        if (StringUtils.isNotEmpty(resourceViewSuffixes)) {
            List<String> strList = Arrays.asList(resourceViewSuffixes.split(","));
            if (!strList.contains(nameSuffix)) {
                logger.error("resource suffix {} not support view,  resource id {}", nameSuffix, resourceId);
                return AjaxResult.error("该文件不支持在线预览");
            }
        }
        // source path
        String resourceFileName = storageOperate.getResourceFileName(loginUser, resource.getFullName());
        logger.info("resource  path is {}", resourceFileName);
        try {
            if (storageOperate.exists(resourceFileName)) {
                List<String> content = storageOperate.vimFile(resourceFileName, skipLineNum, limit);
                result.put("fileName", resource.getFileName());
                result.put("content", String.join("\n", content));
            } else {
                logger.error("read file {} not exist in storage", resourceFileName);
                return AjaxResult.error("读取的文件不存在");
            }

        } catch (Exception e) {
            logger.error("Resource {} read failed", resourceFileName, e);
            return AjaxResult.error("读取hdfs文件操作异常");
        }
        return result;
    }

    @Override
    public AjaxResult queryFile(String loginUser, String fullName, Integer id) {
        if (StringUtils.isBlank(fullName) && id == null) {
            return AjaxResult.error("请求参数异常");
        }
        Resource resource;
        if (StringUtils.isNotBlank(fullName)) {
            List<Resource> resourceList = fileMapper.queryResource(fullName);
            if (CollectionUtils.isEmpty(resourceList)) {
                return AjaxResult.error("查询的文件不存在");
            }
            resource = resourceList.get(0);
        } else {
            resource = fileMapper.selectById(id);
            if (resource == null) {
                return AjaxResult.error("查询的文件不存在");
            }
            resource = fileMapper.selectByPId(resource.getPid());
            if (resource == null) {
                return AjaxResult.error("查询文件的父目录不存在");
            }
        }
        return AjaxResult.success();
    }

    @Override
    public AjaxResult verifyFileName(String fullName, String loginUser) {
        if (checkFileExists(fullName)) {
            logger.error("file name:{} has exist, can't create again.", RegexUtils.escapeNRT(fullName));
            return AjaxResult.error("文件已存在");
        } else {
            try {
                String filename = storageOperate.getFileName(loginUser, fullName);
                if (storageOperate.exists(filename)) {
                    // throw new ServiceException("文件已存在");
                    return AjaxResult.error("文件已存在");
                }
            } catch (Exception e) {
                logger.error("verify resource failed  and the reason is {}", e.getMessage());
                return AjaxResult.error("存储文件的过程出现异常");
            }
        }
        return AjaxResult.success();
    }

    /*    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult delete(String loginUser, int resourceId) {
        Resource resource = fileMapper.selectById(resourceId);
        if (resource == null) {
            return AjaxResult.error("删除的文件不存在");
        }
        // get all resource id of process definitions those is released
        List<Map<String, Object>> list = processDefinitionMapper.listResources();
        Map<Integer, Set<Long>> resourceProcessMap = ResourceProcessDefinitionUtils.getResourceProcessDefinitionMap(list);
        Set<Integer> resourceIdSet = resourceProcessMap.keySet();
        List<Integer> allChildren = listAllChildren(resource, true);
        Integer[] needDeleteResourceIdArray = allChildren.toArray(new Integer[allChildren.size()]);

        if (resourceIdSet.contains(resource.getPid())) {
            logger.error("can't be deleted,because it is used of process definition");
            return AjaxResult.error("文件在任务中使用，不能删除");
        }
        resourceIdSet.retainAll(allChildren);
        if (CollectionUtils.isNotEmpty(resourceIdSet)) {
            logger.error("can't be deleted,because it is used of process definition");
            for (Integer resId : resourceIdSet) {
                logger.error("resource id:{} is used of process definition {}",resId,resourceProcessMap.get(resId));
            }
            return AjaxResult.error("文件在任务中使用，不能删除");
        }

        // get hdfs file by type
        String storageFilename = storageOperate.getFileName(loginUser, resource.getFullName());
        //delete data in database
       *//* fileMapper.selectBatchIds(Arrays.asList(needDeleteResourceIdArray)).forEach(item -> {
            updateParentResourceSize(item, item.getSize() * -1);
        });*//*
        fileMapper.deleteIds(needDeleteResourceIdArray);
        try {
            storageOperate.delete(storageFilename, true);
        } catch (IOException exception) {
            exception.printStackTrace();
            throw new ServiceException("删除hdfs对应文件出现异常"+ exception.getMessage());
        }
        return AjaxResult.success();
    }*/

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult updateFile(String loginUser, int fileId, String name, String desc, MultipartFile file) {
        Resource resource = fileMapper.selectById(fileId);
        if (resource == null) {
            return AjaxResult.error("修改的文件不存在");
        }

        //check resource already exists
        String originFullName = resource.getFullName();
        String originResourceName = resource.getFileName();

        String fullName = String.format(FileConstants.FORMAT_SS, originFullName.substring(0, originFullName.lastIndexOf(FileConstants.FOLDER_SEPARATOR) + 1), name);
        if (!originResourceName.equals(name) && checkFileExists(fullName)) {
            logger.error("resource {} already exists, can't recreate", name);
            return AjaxResult.error("文件名已存在");
        }

        AjaxResult result = verifyFile(name, file);
        if (!result.isSuccess()) {
            return result;
        }
        // get the path of origin file in storage
        String originFileName = storageOperate.getFileName(loginUser, originFullName);
        try {
            if (!storageOperate.exists(originFileName)) {
                logger.error("{} not exist", originFileName);
                return AjaxResult.error("文件不存在");
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException("操作hdfs出现异常");
        }
        // updateResource data
        Date now = new Date();
        long originFileSize = resource.getSize();

        resource.setFileName(name);
        resource.setFullName(fullName);
        resource.setRemark(desc);
        resource.setUpdateBy(loginUser);
        if (file != null) {
            resource.setSize(file.getSize());
        }
        try {
            fileMapper.updateById(resource);
            if (resource.isDirectory()) {
                List<Integer> childrenResource = listAllChildren(resource, false);
                if (CollectionUtils.isNotEmpty(childrenResource)) {
                    String matcherFullName = Matcher.quoteReplacement(fullName);
                    List<Resource> childResourceList;
                    Integer[] childResIdArray = childrenResource.toArray(new Integer[childrenResource.size()]);
                    List<Resource> resourceList = fileMapper.listFileByIds(childResIdArray);
                    childResourceList = resourceList.stream().map(t -> {
                        t.setFullName(t.getFullName().replaceFirst(originFullName, matcherFullName));
                        t.setUpdateTime(now);
                        return t;
                    }).collect(Collectors.toList());
                    fileMapper.batchUpdateFile(childResourceList);
                }
            }
        } catch (Exception e) {
            throw new ServiceException("更新文件异常" + e.getMessage());
        }
        // if name unchanged, return directly without moving on HDFS
        if (originResourceName.equals(name) && file == null) {
            return result;
        }

        if (file != null) {
            // fail upload
            if (!upload(loginUser, fullName, file)) {
                logger.error("upload resource: {} file: {} failed.", name, RegexUtils.escapeNRT(file.getOriginalFilename()));
                throw new ServiceException(String.format("upload resource: %s file: %s failed.", name, file.getOriginalFilename()));
            }
            if (!fullName.equals(originFullName)) {
                try {
                    storageOperate.delete(originFileName, false);
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    throw new ServiceException(String.format("delete resource: %s failed.", originFullName));
                }
            }
            updateParentResourceSize(resource, resource.getSize() - originFileSize);
            return result;
        }

        // get the path of dest file in hdfs
        String destHdfsFileName = storageOperate.getFileName(loginUser, fullName);
        try {
            logger.info("start  copy {} -> {}", originFileName, destHdfsFileName);
            storageOperate.copy(originFileName, destHdfsFileName, true, true);
        } catch (Exception e) {
            logger.error(MessageFormat.format(" copy {0} -> {1} fail", originFileName, destHdfsFileName), e);
            throw new ServiceException("hdfs 复制文件出现异常" + e.getMessage());
        }
        return AjaxResult.success();
    }

    List<Integer> listAllChildren(Resource resource, boolean containSelf) {
        List<Integer> childList = new ArrayList<>();
        if (resource.getId() != -1 && containSelf) {
            childList.add(resource.getId());
        }
        if (resource.isDirectory()) {
            listAllChildren(resource.getId(), childList);
        }
        return childList;
    }

    void listAllChildren(int fileId, List<Integer> childList) {
        List<Integer> children = fileMapper.listChildren(fileId);
        for (int childId : children) {
            childList.add(childId);
            listAllChildren(childId, childList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult createFile(String loginUser, String name, String desc, MultipartFile file, int pid, String currentDir) {
        //非父目录时，父目录不存在
        if (pid != -1) {
            Resource parentResource = fileMapper.selectByPId(pid);
            if (parentResource == null) {
                return AjaxResult.error("父目录不存在");
            }
        }

        AjaxResult result = verifyFile(name, file);
        if (!result.isSuccess()) {
            return result;
        }
        // check resource name exists
        String fullName = getFullName(currentDir, name);
        if (checkFileExists(fullName)) {
            logger.error("file {} has exist, can't recreate", RegexUtils.escapeNRT(name));
            return AjaxResult.error("文件已存在！");
        }
        if (fullName.length() > FileConstants.FILE_FULL_NAME_MAX_LENGTH) {
            logger.error("resource {}'s full name {}' is longer than the max length {}", RegexUtils.escapeNRT(name), fullName, FileConstants.FILE_FULL_NAME_MAX_LENGTH);
            return AjaxResult.error("文件名超过限制长度");
        }
        //存储文件信息
        Resource resource = new Resource();
        resource.setRemark(desc);
        resource.setCreateBy(loginUser);
        resource.setFileName(name);
        resource.setSize(file.getSize());
        resource.setPid(pid);
        resource.setDirectory(false);
        resource.setFullName(fullName);

        try {
            fileMapper.createDirectory(resource);
            updateParentResourceSize(resource, resource.getSize());
        } catch (Exception e) {
            logger.error("resource already exists, can't recreate ", e);
            throw new ServiceException("resource already exists, can't recreate");
        }
        // fail upload
        if (!upload(loginUser, fullName, file)) {
            logger.error("upload resource: {} file: {} failed.", RegexUtils.escapeNRT(name), RegexUtils.escapeNRT(file.getOriginalFilename()));
            throw new ServiceException(String.format("upload resource: %s file: %s failed.", name, file.getOriginalFilename()));
        }
        return AjaxResult.success();
    }

    /**
     * upload file to hdfs
     *
     * @param loginUser login user
     * @param fullName  full name
     * @param file      file
     */
    private boolean upload(String loginUser, String fullName, MultipartFile file) {
        // save to local
        String fileSuffix = Files.getFileExtension(file.getOriginalFilename());
        String nameSuffix = Files.getFileExtension(fullName);
        // determine file suffix
        if (!fileSuffix.equalsIgnoreCase(nameSuffix)) {
            return false;
        }
        // random file name
        String localFilename = FileUtils.getUploadFilename(loginUser, UUID.randomUUID().toString());

        // save file to hdfs, and delete original file
        String fileName = storageOperate.getFileName(loginUser, fullName);
        String resourcePath = storageOperate.getDir(loginUser);
        try {
            // if tenant dir not exists
            if (!storageOperate.exists(resourcePath)) {
                storageOperate.createDirIfNotExists(loginUser);
            }
            com.example.dtt.utils.files.FileUtils.copyInputStreamToFile(file, localFilename);
            storageOperate.upload(localFilename, fileName, true, true);
        } catch (Exception e) {
            FileUtils.deleteFile(localFilename);
            logger.error(e.getMessage(), e);
            return false;
        }
        return true;
    }

    /**
     * update the folder's size of the resource
     *
     * @param resource the current resource
     * @param size     size
     */
    private void updateParentResourceSize(Resource resource, long size) {
        if (resource.getSize() > 0) {
            String[] splits = resource.getFullName().split("/");
            for (int i = 1; i < splits.length; i++) {
                String parentFullName = Joiner.on("/").join(Arrays.copyOfRange(splits, 0, i));
                if (StringUtils.isNotBlank(parentFullName)) {
                    List<Resource> resources = fileMapper.queryResource(parentFullName);
                    if (CollectionUtils.isNotEmpty(resources)) {
                        Resource parentResource = resources.get(0);
                        if (parentResource.getSize() + size >= 0) {
                            parentResource.setSize(parentResource.getSize() + size);
                        } else {
                            parentResource.setSize(0L);
                        }
                        fileMapper.updateById(parentResource);
                    }
                }
            }
        }
    }

    //创建文件
    private AjaxResult verifyFile(String fileName, MultipartFile file) {
       /* if (FileUtils.directoryTraversal(fileName)) {
        }*/

        if (file != null && FileUtils.directoryTraversal(Objects.requireNonNull(file.getOriginalFilename()))) {
            logger.error("file original name {} verify failed", file.getOriginalFilename());
            return AjaxResult.error("文件名校验失败");
        }

        if (file != null) {
            // file is empty
            if (file.isEmpty()) {
                logger.error("file is empty: {}", RegexUtils.escapeNRT(file.getOriginalFilename()));
                return AjaxResult.error("文件不能为空");
            }

            // file suffix
            String fileSuffix = Files.getFileExtension(file.getOriginalFilename());
            String nameSuffix = Files.getFileExtension(fileName);

            // determine file suffix
            if (!fileSuffix.equalsIgnoreCase(nameSuffix)) {
                // rename file suffix and original suffix must be consistent
                logger.error("rename file suffix and original suffix must be consistent: {}", RegexUtils.escapeNRT(file.getOriginalFilename()));
                return AjaxResult.error("文件后缀不允许修改");
            }

            if (file.getSize() > FileConstants.MAX_FILE_SIZE) {
                logger.error("file size is too large: {}", RegexUtils.escapeNRT(file.getOriginalFilename()));
                return AjaxResult.error("超出文件大小的限制");
            }
        }
        return AjaxResult.success();
    }

    /**
     * 创建文件夹
     *
     * @param loginUser
     * @param fileName
     * @param description
     * @param pid
     * @param currentDir
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult createDirectory(String loginUser, String fileName, String description, int pid, String currentDir) {
        String fullName = getFullName(currentDir, fileName);
        //判断文件夹是否存在
        //数据库全文件名是否存在
        if (checkFileExists(fullName)) {
            logger.error("file directory {} has exist, can't recreate", fullName);
            return AjaxResult.error("文件名已存在请确认");
        }
        try {
            //hdfs路径下文件是否存在
            String hdfsFileName = storageOperate.getFileName(loginUser, fullName);
            if (storageOperate.exists(hdfsFileName)) {
                return AjaxResult.error("hdfs 路径下该文件已存在");
            }
        } catch (Exception e) {
            throw new ServiceException("访问hdfs异常");
        }

        //非父目录时，父目录不存在
        if (pid != -1) {
            Resource parentResource = fileMapper.selectByPId(pid);
            if (parentResource == null) {
                return AjaxResult.error("父目录不存在");
            }
        }
        Resource resource = new Resource();
        resource.setDirectory(true);
        resource.setSize(0);
        resource.setCreateBy(loginUser);
        resource.setFileName(fileName);
        resource.setPid(pid);
        resource.setFullName(fullName);
        resource.setRemark(description);
        try {
            //保存信息
            fileMapper.createDirectory(resource);
        } catch (DuplicateKeyException e) {
            logger.error("file directory {} has exist, can't recreate", fullName);
            return AjaxResult.error("文件夹已存在");
        } catch (Exception e) {
            logger.error("file already exists, can't recreate ", e);
            throw new ServiceException("file already exists, can't recreate");
        }
        //create directory in storage
        createDirectory(loginUser, fullName);
        return AjaxResult.success("创建成功");
    }

    private void createDirectory(String loginUser, String fullName) {
        String directoryName = storageOperate.getFileName(loginUser, fullName);
        String resourceRootPath = storageOperate.getDir(loginUser);
        try {
            if (!storageOperate.exists(resourceRootPath)) {
                storageOperate.createDirIfNotExists(loginUser);
            }

            if (!storageOperate.mkdir(directoryName)) {
                logger.error("create resource directory {}  failed", directoryName);
                throw new ServiceException(String.format("create resource directory: %s failed.", directoryName));
            }
        } catch (Exception e) {
            logger.error("create resource directory {}  failed", directoryName);
            throw new ServiceException(String.format("create resource directory: %s failed.", directoryName));
        }
    }

    private String getFullName(String currentDir, String name) {
        return currentDir.equals(FileConstants.FOLDER_SEPARATOR) ? String.format(FileConstants.FORMAT_SS, currentDir, name) : String.format(FileConstants.FORMAT_S_S, currentDir, name);
    }

    //校验文件名是否已存在（不包含已删除文件）
    private boolean checkFileExists(String fullName) {
        Boolean existResource = fileMapper.existFile(fullName);
        return Boolean.TRUE.equals(existResource);
    }
}
