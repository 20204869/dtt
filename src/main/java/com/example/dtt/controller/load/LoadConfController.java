package com.example.dtt.controller.load;

import com.example.dtt.annotation.Log;
import com.example.dtt.controller.base.BaseController;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.entity.load.LoadFileConf;
import com.example.dtt.domain.page.TableDataInfo;
import com.example.dtt.enums.BusinessType;
import com.example.dtt.service.load.LoadFileConfService;
import com.example.dtt.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 导入文件配置管理
 */
@RestController
@RequestMapping("/load/conf")
public class LoadConfController extends BaseController {
    @Autowired
    private LoadFileConfService confService;

    /**
     * 获取配置文件列表
     */
    @PreAuthorize("@ss.hasPermi('load:conf:list')")
    @GetMapping("/list")
    public TableDataInfo list(LoadFileConf conf) {
        startPage();
        List<LoadFileConf> list = confService.LoadFileConfList(conf);
        return getDataTable(list);
    }

    /**
     * 新增配置
     */
    @PreAuthorize("@ss.hasPermi('load:conf:add')")
    @Log(title = "新增配置", businessType = BusinessType.INSERT)
    @PostMapping()
    public AjaxResult add(@Validated @RequestBody LoadFileConf conf) {
        //不允许新增相同HDFS路径和相同Hive表的导入文件配置
        int hdfsCount = confService.checkConfByHdfsPath(conf.getHdfsPath());
        int hiveCount = confService.checkConfByHiveTable(conf.getHiveTable());
        if (hdfsCount > 0 || hiveCount > 0) {
            return AjaxResult.error("请确认新增的配置是否与已存在的HDFS路径或Hive表重复");
        }
        conf.setCreateBy(getUsername());
        conf.setCreateTime(new Date());
        return toAjax(confService.saveLoadFileConf(conf));
    }

    /**
     * 根据编号获取配置信息
     */
    @PreAuthorize("@ss.hasPermi('load:conf:query')")
    @GetMapping(value = {"/", "/{confId}"})
    public AjaxResult getConfInfo(@PathVariable(value = "confId", required = false) Long confId) {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isNotNull(confId)) {
            ajax.put(AjaxResult.DATA_TAG, confService.selectConfById(confId));
            ajax.put("confIds", confService.selectConfById(confId));
        }
        return ajax;
    }

    /**
     * 查询所有的配置信息
     */
    @GetMapping(value = "/get")
    public AjaxResult getConf() {
        List<LoadFileConf> data = confService.getConf();
        if (StringUtils.isNull(data)) {
            data = new ArrayList<>();
        }
        return AjaxResult.success(data);
    }

    /**
     * 修改配置
     */
    @PreAuthorize("@ss.hasPermi('load:conf:edit')")
    @Log(title = "修改配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody LoadFileConf conf) {
        conf.setUpdateBy(getUsername());
        return toAjax(confService.updateConf(conf));
    }

    /**
     * 删除配置
     */
    @PreAuthorize("@ss.hasPermi('load:conf:remove')")
    @Log(title = "删除配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{confIds}")
    public AjaxResult remove(@PathVariable Long[] confIds) {
        return toAjax(confService.deleteConfByIds(confIds));
    }

}
