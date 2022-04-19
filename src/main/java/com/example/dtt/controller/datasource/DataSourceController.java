package com.example.dtt.controller.datasource;

import com.example.dtt.annotation.Log;
import com.example.dtt.controller.base.BaseController;
import com.example.dtt.datasource.common.BaseDataSourceParamDTO;
import com.example.dtt.datasource.common.datasource.ConnectionParam;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.entity.datasource.DataSource;
import com.example.dtt.domain.page.TableDataInfo;
import com.example.dtt.enums.BusinessType;
import com.example.dtt.service.datasource.DataSourceService;
import com.example.dtt.utils.sql.DataSourceUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
@RequestMapping("/datasource/datasource")
public class DataSourceController extends BaseController {

    @Autowired
    private DataSourceService sourceService;

    /**
     * 获取数据源列表
     */
    @PreAuthorize("@ss.hasPermi('datasource:datasource:list')")
    @GetMapping("/list")
    public TableDataInfo list(DataSource source) {
        startPage();
        List<DataSource> list = sourceService.queryDataSourceListPaging(source);
        return getDataTable(list);
    }

    /**
     * 新增数据源
     */
    @PreAuthorize("@ss.hasPermi('datasource:datasource:add')")
    @Log(title = "数据源", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BaseDataSourceParamDTO dataSourceParam) {
        return sourceService.createDataSource(getUsername(), dataSourceParam);
    }

    /**
     * 修改数据源
     */
    @PreAuthorize("@ss.hasPermi('datasource:datasource:edit')")
    @Log(title = "数据源", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/{id}")
    public AjaxResult edit(@PathVariable(value = "id") Integer id, @Validated @RequestBody BaseDataSourceParamDTO dataSourceParam) {
        dataSourceParam.setId(id);
        return toAjax(sourceService.updateDataSource(id, dataSourceParam, getUsername()));
    }

    /**
     * 获取数据源详情
     *
     * @param id
     * @return
     */
    @PreAuthorize("@ss.hasPermi('datasource:datasource:query')")
    @GetMapping(value = {"/","/{id}"})
    public AjaxResult queryDataSource(@PathVariable(value = "id", required = false) Integer id) {
        if (id == null){
            return AjaxResult.success();
        }
        return sourceService.queryDataSource(id);
    }

    /**
     * 测试数据源连接
     *
     * @param dataSourceParam
     * @return
     */
    @PreAuthorize("@ss.hasPermi('datasource:datasource:query')")
    @PostMapping(value = "/connect")
    public AjaxResult connectDataSource(@RequestBody BaseDataSourceParamDTO dataSourceParam) {
        DataSourceUtils.checkDatasourceParam(dataSourceParam);
        ConnectionParam connectionParams = DataSourceUtils.buildConnectionParams(dataSourceParam);
        return sourceService.checkConnection(dataSourceParam.getType(), connectionParams);
    }

    /**
     * 删除数据源
     *
     * @param ids
     * @return
     */
    @PreAuthorize("@ss.hasPermi('datasource:datasource:delete')")
    @Log(title = "数据源", businessType = BusinessType.DELETE)
    @DeleteMapping(value = "/{ids}")
    public AjaxResult delete(@PathVariable("ids") Long[] ids) {
        return toAjax(sourceService.deleteDataSourceByIds(ids));
    }

}
