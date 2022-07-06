package com.example.dtt.controller.file;

import com.example.dtt.annotation.Log;
import com.example.dtt.controller.base.BaseController;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.entity.file.Resource;
import com.example.dtt.domain.page.TableDataInfo;
import com.example.dtt.enums.BusinessType;
import com.example.dtt.service.file.FileService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件管理
 */
@RestController
@RequestMapping("/resources/file")
public class FileController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileService fileService;

    /**
     * 查询文件资源列表
     */
    @PreAuthorize("@ss.hasPermi('resources:file:list')")
    @GetMapping("/list")
    public TableDataInfo getFile(Resource resource) {
        startPage();
        List<Resource> list = fileService.fileList(resource);
        return getDataTable(list);
    }

    /**
     * 创建文件夹
     */
    @PreAuthorize("@ss.hasPermi('resources:file:add')")
    @Log(title = "文件夹", businessType = BusinessType.INSERT)
    @PostMapping(value = "/directory")
    public AjaxResult createDirectory(@RequestParam(value = "fileName") String fileName,
                                          @RequestParam(value = "description", required = false) String description,
                                          @RequestParam(value = "pid") int pid,
                                          @RequestParam(value = "currentDir") String currentDir) {
        return fileService.createDirectory(getUsername(), fileName, description, pid, currentDir);
    }

    /**
     * 创建文件
     */
    @PreAuthorize("@ss.hasPermi('resources:file:add')")
    @PostMapping()
    public AjaxResult createFile(@RequestParam(value = "fileName") String fileName,
                                         @RequestParam(value = "description", required = false) String description,
                                         @RequestParam("file") MultipartFile file,
                                         @RequestParam(value = "pid") int pid,
                                         @RequestParam(value = "currentDir") String currentDir) {
        return fileService.createFile(getUsername(), fileName, description,file, pid, currentDir);
    }

    /**
     * 更新文件
     */
    @PreAuthorize("@ss.hasPermi('resources:file:query')")
    @PutMapping(value = "/{id}")
    public AjaxResult updateFile(@PathVariable(value = "id") int fileId,@RequestParam(value = "fileName") String fileName,
                                         @RequestParam(value = "description", required = false) String description,
                                         @RequestParam(value = "file", required = false) MultipartFile file) {
        return fileService.updateFile(getUsername(), fileId, fileName, description,file);
    }

    /**
     * 删除文件

    @DeleteMapping(value = "/{id}")
    public AjaxResult deleteResource(@PathVariable(value = "id") int fileId ) throws Exception {
        return fileService.delete(getUsername(), fileId);
    }*/

    /**
     * 按文件名校验文件
     */
    @PreAuthorize("@ss.hasPermi('resources:file:query')")
    @GetMapping(value = "/verify-name")
    public AjaxResult verifyFileName(@RequestParam(value = "fullName") String fullName) {
        return fileService.verifyFileName(fullName,getUsername());
    }

    /**
     * 检索文件
     */
    @PreAuthorize("@ss.hasPermi('resources:file:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult queryFile(@RequestParam(value = "fullName", required = false) String fullName,@PathVariable(value = "id", required = false) Integer id) {
        return fileService.queryFile(getUsername(),fullName, id);
    }

    /**
     * 预览文件内容
     */
    @PreAuthorize("@ss.hasPermi('resources:file:query')")
    @GetMapping(value = "/view/{id}")
    public AjaxResult viewFile(@PathVariable(value = "id") int fileId,
                                   @RequestParam(value = "skipLineNum") int skipLineNum,
                                   @RequestParam(value = "limit") int limit) {
        return fileService.viewFile(getUsername(),fileId, skipLineNum, limit);
    }

    /**
     * 创建文件
     */
    @PreAuthorize("@ss.hasPermi('resources:file:query')")
    @PostMapping(value = "/online-create")
    public AjaxResult onlineCreateFile(@RequestParam(value = "fileName") String fileName,
                                       @RequestParam(value = "suffix") String fileSuffix,
                                       @RequestParam(value = "description", required = false) String description,
                                       @RequestParam(value = "content") String content,
                                       @RequestParam(value = "pid") int pid,
                                       @RequestParam(value = "currentDir") String currentDir
    ) {
        if (StringUtils.isEmpty(content)) {
            logger.error("resource file contents are not allowed to be empty");
            return AjaxResult.error("文件内容不允许为空");
        }
        return fileService.onlineCreateFile(getUsername(), fileName, fileSuffix, description, content, pid, currentDir);
    }

    /**
     * 在线编辑文件
     */
    @PreAuthorize("@ss.hasPermi('resources:file:query')")
    @PutMapping(value = "/update-content/{id}")
    public AjaxResult updateResourceContent(@PathVariable(value = "id") int fileId, @RequestParam(value = "content") String content) {
        if (StringUtils.isEmpty(content)) {
            logger.error("The resource file contents are not allowed to be empty");
            return AjaxResult.error("文件内容不能为空");
        }
        return fileService.updateFileContent(getUsername(),fileId, content);
    }

    /**
     * 下载资源文件
     */
    @GetMapping(value = "/download/{id}")
    @ResponseBody
    public ResponseEntity downloadFile(@PathVariable(value = "id") int fileId) throws Exception  {
        org.springframework.core.io.Resource file = fileService.downloadResource(getUsername(),fileId);
        if (file == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("文件不存在，无法下载");
        }
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    /**
     * 根据id查询资源文件
     */
    @PreAuthorize("@ss.hasPermi('resources:file:query')")
    @GetMapping(value = "/query/{id}")
    public AjaxResult queryResourceById(@PathVariable(value = "id", required = true) Integer id) {
        return fileService.queryFileById(getUsername(),id);
    }
}
