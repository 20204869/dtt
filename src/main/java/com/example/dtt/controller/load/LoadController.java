package com.example.dtt.controller.load;

import com.example.dtt.annotation.Log;
import com.example.dtt.common.config.DttConfig;
import com.example.dtt.constant.load.LoadFileConstants;
import com.example.dtt.controller.base.BaseController;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.entity.load.LoadFile;
import com.example.dtt.domain.entity.load.LoadFileConf;
import com.example.dtt.domain.page.TableDataInfo;
import com.example.dtt.enums.BusinessType;
import com.example.dtt.service.load.LoadFileConfService;
import com.example.dtt.service.load.LoadFileService;
import com.example.dtt.utils.HadoopUtils;
import com.example.dtt.utils.poi.ExcelReaderUtil;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 导入文件到hive表 业务处理
 */
@RestController
@RequestMapping("/load/file")
public class LoadController extends BaseController {
    @Autowired
    private LoadFileService loadFileService;

    @Autowired
    private LoadFileConfService confService;

    /**
     * 获取用户列表
     */
    @PreAuthorize("@ss.hasPermi('load:file:list')")
    @GetMapping("/list")
    public TableDataInfo list(LoadFile file) {
        startPage();
        List<LoadFile> list = loadFileService.LoadFileList(file);
        return getDataTable(list);
    }

    /**
     * 上传文件
     */
    @Log(title = "上传文件", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('load:file:load')")
    @PostMapping("/save")
    public AjaxResult saveFile(@RequestParam("file") MultipartFile file, @RequestParam("confId") Long confId) {
        FileSystem fs = null;
        Path dstPath = null;
        try {
            if (file.isEmpty()) {
                return AjaxResult.error("上传文件不能为空，请选择上传文件!");
            }
            String fileNameSuffix = file.getOriginalFilename().split("\\+|\\*|\\-|/|%|\\_|\\#|\\ ")[1].split("\\.")[0];
            //根据传入的confId 将上传的文件存入不同的路径下
            LoadFileConf fileConf = confService.selectConfById(confId);
       /* String filePath = LoadFileUtils.getFilePath(confId);
        String fileName = LoadFileUtils.getFileName(confId, fileNameSuffix);*/
            String filePath = fileConf.getHdfsPath();
            if (!filePath.endsWith("/")) {
                filePath = fileConf.getHdfsPath() + "/";
            }
            //hive表名+日期作为文件名写入hdfs
            String fileName = fileConf.getHiveTable().split("\\.")[1] + "_" + fileNameSuffix;
            //List<String[]> list = FileExcel2CsvUtils.readExcel(file);
            // File directory = new File("");//参数为空
            // String courseFile = DemoConfig.getUploadPath(confId);
            //String courseFile = directory.getCanonicalPath();
          /*  boolean excel = FileExcel2CsvUtils.createCsvFile(list, courseFile, fileName);
            if (!excel) {
                logger.error("Excel 转 CSV 异常,请检查后再进行上线");
                return AjaxResult.error("上传文件异常，请联系管理员");
            }*/
            String courseFile = DttConfig.getProfile() + filePath;
            /**
             * 删除上次导入旧文件
             */
            String fileNameTmp = file.getOriginalFilename();
            String dst = courseFile + fileName + ".csv";
            File preFile = new File(dst);
            if (preFile.exists()) {
                preFile.delete();
            }
            ExcelReaderUtil.readExcel(file, courseFile, fileName);

            // System.setProperty("hadoop.home.dir", "D:\\hadoop-common-2.2.0-bin");
            //调用hadoopAPI
            //  fs = HadoopUtils.getDevFS();
            fs = HadoopUtils.getProdFS();
            Path srcPath = new Path(dst);
            dstPath = new Path(filePath);
            //判断HDFS路径是否存在，不存在则创建
            if (!fs.exists(dstPath)) {
                fs.mkdirs(dstPath);
            }
            fs.copyFromLocalFile(srcPath, dstPath);
            LoadFile loadFile = new LoadFile();
            String loadUser = getLoginUser().getUser().getUserName();
            //String fileName = FileUploadUtils.upload(DemoConfig.getUploadPath(), file);
            loadFile.setFileName(fileNameTmp);
            loadFile.setFilePath(dst);
            loadFile.setConfId(confId);
            loadFile.setLoadUser(loadUser);
            return toAjax(loadFileService.saveFile(loadFile));
        } catch (Exception e) {
            try {
                if (fs.exists(dstPath)) {
                    fs.delete(dstPath, false);
                }
            } catch (IOException exception) {
                exception.printStackTrace();
                logger.error("删除上传失败但上传hdfs 成功的文件！");
            }
            logger.error("上传文件异常：" + e.getMessage());
            return AjaxResult.error("请校验配置信息及上传传的Excel文件命名格式！");
        }
    }

    /**
     * 删除文件
     */
    @PreAuthorize("@ss.hasPermi('load:file:delete')")
    @Log(title = "删除文件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{fileIds}")
    public AjaxResult remove(@PathVariable Long[] fileIds) {
        List<LoadFile> loadFiles = loadFileService.loadFilesByIds(fileIds);
        if (CollectionUtils.isEmpty(loadFiles)) {
            return AjaxResult.error("删除文件不存在，请确认后删除");
        }
        // System.setProperty("hadoop.home.dir", "D:\\hadoop-common-2.2.0-bin");
        //调用hadoopAPI
        FileSystem fs = HadoopUtils.getProdFS();
        String filePrefix = DttConfig.getProfile();
        // FileSystem fs = HadoopUtils.getDevFS();
        for (LoadFile loadFile : loadFiles) {
            Long confId = loadFile.getConfId();
            String filePath = loadFile.getFilePath().substring(filePrefix.length());
            String path = LoadFileConstants.HDFS_NAME_PROD + filePath;
            // String path = LoadFileConstants.HDFS_NAME_DEV + fileName;
            Path dstPath = new Path(path);
            try {
                if (fs.exists(dstPath)) {
                    fs.delete(dstPath, false); //非递归删除
                }
            } catch (Exception e) {
                Path srcPath = new Path(filePath);
                try {
                    fs.copyFromLocalFile(srcPath, dstPath);
                } catch (IOException exception) {
                    exception.printStackTrace();
                    logger.error("恢复删除失败的文件异常：" + e.getMessage());
                }
                logger.error("删除文件异常：" + e.getMessage());
                return AjaxResult.error("删除文件异常，请联系管理员");
            }
        }
        return toAjax(loadFileService.deleteFileByIds(fileIds));
    }
}
