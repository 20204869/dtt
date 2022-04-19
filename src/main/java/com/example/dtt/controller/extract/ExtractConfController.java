package com.example.dtt.controller.extract;

import com.example.dtt.annotation.Log;
import com.example.dtt.controller.base.BaseController;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.entity.extract.ExtractConf;
import com.example.dtt.domain.entity.system.SysUser;
import com.example.dtt.domain.page.TableDataInfo;
import com.example.dtt.enums.BusinessType;
import com.example.dtt.service.extract.ExtractConfService;
import com.example.dtt.service.extract.ExtractTemplateService;
import com.example.dtt.service.system.ISysUserService;
import com.example.dtt.utils.StringUtils;
import com.example.dtt.utils.poi.ExcelUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 取数模板
 */
@RestController
@RequestMapping("/extract/conf")
public class ExtractConfController extends BaseController {
    @Autowired
    private ExtractConfService confService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ExtractTemplateService templateService;

    /**
     * 获取取数模板列表
     */
    @PreAuthorize("@ss.hasPermi('extract:conf:list')")
    @GetMapping("/list")
    public TableDataInfo list(ExtractConf conf) {
        startPage();
        List<ExtractConf> list = confService.confListPaging(conf);
        return getDataTable(list);
    }

    /**
     * 根据根据模板id获取模板详情
     */
    @PreAuthorize("@ss.hasPermi('extract:conf:query')")
    @GetMapping(value = {"/", "/{id}"})
    public AjaxResult getInfo(@PathVariable(value = "id", required = false) Long id) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("users", userService.selectUserAll());
        if (StringUtils.isNotNull(id)) {
            ExtractConf conf = confService.queryConfById(id);
            ajax.put(AjaxResult.DATA_TAG, conf);
            ajax.put("userIds", confService.selectUserListById(id));
        }
        return ajax;
    }

    /**
     * 新增模板
     */
    @PreAuthorize("@ss.hasPermi('extract:conf:add')")
    @Log(title = "取数模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody ExtractConf conf) {
        ExtractConf existConf = confService.queryConfByName(conf.getTemplateName());
        if (existConf != null) {
            return AjaxResult.error("取数模板名称已存在！");
        }
        conf.setCreateBy(getUsername());
        return toAjax(confService.saveConf(conf));
    }

    /**
     * 修改模板
     */
    @PreAuthorize("@ss.hasPermi('extract:conf:edit')")
    @Log(title = "取数模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody ExtractConf conf) {
        conf.setUpdateBy(getUsername());
        return toAjax(confService.updateConf(conf));
    }

    /**
     * 根据模板分配用户
     */
    @PreAuthorize("@ss.hasPermi('extract:conf:query')")
    @GetMapping("/userTemplate/{id}")
    public AjaxResult userTemplate(@PathVariable("id") Long id) {
        AjaxResult ajax = AjaxResult.success();
        ExtractConf conf = confService.queryConfById(id);
        List<SysUser> users = userService.selectUsersById(id);
        ajax.put("conf", conf);
        ajax.put("users", users);
        return ajax;
    }

    /**
     * 根据用户id 查询属于该用户的取数模板
     *
     * @return
     */
    @PreAuthorize("@ss.hasPermi('extract:conf:query')")
    @GetMapping("/template")
    public TableDataInfo getUserTemplate(ExtractConf conf) {
        startPage();
        List<ExtractConf> userTemplate = confService.confListByUserId(conf.getTemplateName(), getUserId());
        return getDataTable(userTemplate);
    }

    /**
     * 取数模板用户
     */
    @PreAuthorize("@ss.hasPermi('extract:conf:edit')")
    @Log(title = "取数用户", businessType = BusinessType.GRANT)
    @PutMapping("/userTemplate")
    public AjaxResult insertUserTemplate(Long id, Long[] userIds) {
        confService.insertUserTemplate(id, userIds);
        return success();
    }

    /**
     * 删除模板
     */
    @PreAuthorize("@ss.hasPermi('extract:conf:remove')")
    @Log(title = "取数模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return AjaxResult.error("请选择需要删除的模板");
        }
        return toAjax(confService.deleteConfByIds(ids));
    }

    @Log(title = "抽取模板", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('extract:template:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, ExtractConf conf) {
        String executeSql = conf.getTemplateSql();
        List<LinkedHashMap<String, Object>> sqlResult = templateService.templateResult(executeSql);
        String fileName = conf.getTemplateName();
        ExcelUtils.exportExcel2007(response, sqlResult, fileName);
    }
}
