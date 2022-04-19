package com.example.dtt.controller.map;

import com.example.dtt.controller.base.BaseController;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.service.map.BusinessService;
import com.example.dtt.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 界面展示表信息
 */
@RestController
@RequestMapping("/map/business")
public class BusinessController extends BaseController {

    @Autowired
    private BusinessService businessService;

    /**
     * 搜索业务表展示
     * @param searchValue
     * @return
     */
    @PreAuthorize("@ss.hasPermi('map:business:query')")
    @GetMapping("/businessTable/{searchValue}")
    public AjaxResult getBusiness(@PathVariable(value = "searchValue") String searchValue) {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isEmpty(searchValue)){
            return AjaxResult.error("请输入查询条件");
        }
        ajax.put("business", businessService.buinessList(searchValue));
        return ajax;
    }

    /**
     * 根据表名获取表详情
     * @param tableName
     * @return
     */
    @PreAuthorize("@ss.hasPermi('map:business:query')")
    @GetMapping("/businessCol/{tableName}")
    public AjaxResult getColsList(@PathVariable(value = "tableName") String tableName) {
        AjaxResult ajax = AjaxResult.success();
        if (StringUtils.isEmpty(tableName)){
            return AjaxResult.error("传入的请求参数有误，请确认！");
        }
        ajax.put("business", businessService.colsFromTableName(tableName));
        return ajax;
    }

}
