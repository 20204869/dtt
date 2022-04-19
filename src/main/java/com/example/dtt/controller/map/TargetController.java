package com.example.dtt.controller.map;

import com.example.dtt.annotation.Log;
import com.example.dtt.constant.system.UserConstants;
import com.example.dtt.controller.base.BaseController;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.entity.map.Target;
import com.example.dtt.domain.entity.system.SysUser;
import com.example.dtt.domain.page.TableDataInfo;
import com.example.dtt.enums.BusinessType;
import com.example.dtt.service.map.TargetService;
import com.example.dtt.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 指标
 */
@RestController
@RequestMapping("/map/target")
public class TargetController extends BaseController {
    @Autowired
    private TargetService targetService;


    /**
     * 获取指标列表
     */
    @PreAuthorize("@ss.hasPermi('map:target:list')")
    @GetMapping("/list")
    public TableDataInfo list(Target target) {
        startPage();
        List<Target> list = targetService.selectTargetList(target);
        return getDataTable(list);
    }

    /**
     * 新增指标
     */
    @PreAuthorize("@ss.hasPermi('map:target:add')")
    @Log(title = "新增指标", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody Target target) {
        if (UserConstants.NOT_UNIQUE.equals(targetService.checkTargetName(target.getTargetName()))) {
            return AjaxResult.error("新增指标'" + target.getTargetName() + "'失败，已存在该指标,请确认！");
        }
        target.setCreateBy(getUsername());
        return toAjax(targetService.saveTarget(target));
    }


    /**
     * 根据指标编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('map:target:query')")
    @GetMapping(value = {"/", "/{targetId}"})
    public AjaxResult getInfo(@PathVariable(value = "targetId", required = false) Long targetId) {
        return AjaxResult.success(targetService.getTargetDetailById(targetId));
    }

    /**
     * 根据结果表查询指标信息
     */
    @PreAuthorize("@ss.hasPermi('map:target:query')")
    @GetMapping(value = {"/table/{resultTable}"})
    public AjaxResult getTarget(@PathVariable(value = "resultTable", required = false) String resultTable) {
        AjaxResult ajax = AjaxResult.success();
        Target target = targetService.getTargetDetailByTable(resultTable);
        ajax.put("target", target);
        return ajax;
    }

    /**
     * 修改指标
     */
    @PreAuthorize("@ss.hasPermi('map:target:edit')")
    @Log(title = "修改指标", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody Target target) {
        if (UserConstants.NOT_UNIQUE.equals(targetService.checkTargetName(target.getTargetName()))) {
            return AjaxResult.error("新增指标'" + target.getTargetName() + "'失败，已存在该指标名！");
        }
        target.setUpdateBy(getUsername());
        return toAjax(targetService.updateTarget(target));
    }

    /**
     * 删除指标
     */
    @PreAuthorize("@ss.hasPermi('map:target:remove')")
    @Log(title = "删除指标", businessType = BusinessType.DELETE)
    @DeleteMapping("/{targetIds}")
    public AjaxResult remove(@PathVariable Long[] targetIds) {
        return toAjax(targetService.deleteTarget(targetIds));
    }


    @Log(title = "指标管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('map:target:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<Target> util = new ExcelUtil<>(Target.class);
        List<Target> targetList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = targetService.importTarget(targetList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<Target> util = new ExcelUtil<>(Target.class);
        util.importTemplateExcel(response, "指标数据");
    }
}
