package com.example.dtt.controller.query;

import com.example.dtt.annotation.DataSource;
import com.example.dtt.annotation.Log;
import com.example.dtt.constant.datasource.Constants;
import com.example.dtt.controller.base.BaseController;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.entity.query.HistoryQuery;
import com.example.dtt.domain.page.TableDataInfo;
import com.example.dtt.enums.BusinessType;
import com.example.dtt.enums.DataSourceType;
import com.example.dtt.service.extract.ExtractTemplateService;
import com.example.dtt.service.query.HistoryQueryService;
import com.example.dtt.utils.JSONUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hive.HiveTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 数据查询 历史查询记录
 */
@RestController
@RequestMapping("/query/history")
public class HistoryQueryController extends BaseController {
    @Autowired
    private HistoryQueryService queryService;

    @Autowired
    private ExtractTemplateService templateService;

    /**
     * 登录用户的历史查询记录列表
     */
    @PreAuthorize("@ss.hasPermi('query:history:list')")
    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        String userName = getUsername();
        List<HistoryQuery> list = queryService.historyQueryByUserName(userName);
        return getDataTable(list);
    }

    /**
     * 保存历史查询记录
     */
    @PreAuthorize("@ss.hasPermi('query:history:add')")
    @Log(title = "历史查询", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody  HistoryQuery query) {
        query.setExecutor(getUsername());
        return toAjax(queryService.saveQuery(query));
    }

    @DataSource(DataSourceType.HIVE)
    @PreAuthorize("@ss.hasPermi('query:history:query')")
    @PostMapping("/excuteSql")
    public AjaxResult querySql(@Validated @RequestBody  HistoryQuery query) {
        AjaxResult ajax = AjaxResult.success();
        String executeSql = query.getQuerySql();
        //hive 权限预处理
        //TODO 用户-hive 权限处理
        List<LinkedHashMap<String, Object>> sqlResult = templateService.runHiveResult(executeSql);
        if (CollectionUtils.isEmpty(sqlResult)){
            ajax.put("sqlResult",new ArrayList<>());
            return ajax;
        }
        ajax.put("sqlResult", JSONUtils.toList(sqlResult));
        return ajax;
    }

}
