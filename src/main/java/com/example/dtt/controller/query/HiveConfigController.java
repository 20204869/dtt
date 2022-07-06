package com.example.dtt.controller.query;

import com.example.dtt.annotation.Log;
import com.example.dtt.controller.base.BaseController;
import com.example.dtt.datasource.common.BaseDataSourceParamDTO;
import com.example.dtt.datasource.common.datasource.ConnectionParam;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.entity.query.HiveUser;
import com.example.dtt.domain.page.TableDataInfo;
import com.example.dtt.enums.BusinessType;
import com.example.dtt.service.query.HiveUserService;
import com.example.dtt.utils.sql.DataSourceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 平台用户与hive用户 关系配置
 */
@RestController
@RequestMapping("/hive/config")
public class HiveConfigController extends BaseController {

    @Autowired
    private HiveUserService service;

    /**
     * 获取关系列表
     */
    @PreAuthorize("@ss.hasPermi('hive:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(HiveUser hiveUser) {
        startPage();
        List<HiveUser> list = service.hiveUserListPaging(hiveUser);
        return getDataTable(list);
    }

    /**
     * 新增关系
     */
    @PreAuthorize("@ss.hasPermi('hive:config:add')")
    @Log(title = "数据源", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody HiveUser hiveUser) {
        hiveUser.setCreateBy(getUsername());
        return service.createHiveUser(hiveUser);
    }

    /**
     * 修改关系
     */
    @PreAuthorize("@ss.hasPermi('hive:config:edit')")
    @Log(title = "数据源", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/{id}")
    public AjaxResult edit(@PathVariable(value = "id") Integer id, @Validated @RequestBody HiveUser hiveUser) {
        return toAjax(service.updateHiveUser(id, hiveUser, getUsername()));
    }

    /**
     * 获取数据源详情
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('hive:config:query')")
    @GetMapping(value = {"/","/{id}"})
    public AjaxResult queryDataSource(@PathVariable(value = "id", required = false) Integer id) {
        if (id == null){
            return AjaxResult.success();
        }
        return service.queryHiveUser(id);
    }


    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @PreAuthorize("@ss.hasPermi('hive:config:delete')")
    @Log(title = "数据源", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{ids}")
    public AjaxResult delete(@PathVariable("ids") Long[] ids) {
        return toAjax(service.deleteHiveUserByIds(ids));
    }

}
