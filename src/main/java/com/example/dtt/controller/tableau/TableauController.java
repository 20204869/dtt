package com.example.dtt.controller.tableau;

import com.example.dtt.controller.base.BaseController;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.entity.tableau.Workbook;
import com.example.dtt.service.tableau.TableauService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 取数模板
 */
@RestController
@RequestMapping("/tableau/web")
public class TableauController extends BaseController {

    @Autowired
    private TableauService tableauService;

    /**
     * 获取tableau 视图渲染路径
     * @param workbookId
     * @return
     */
    @PreAuthorize("@ss.hasPermi('tableau:web:query')")
    @GetMapping(value = {"/viewUrl/{workbookId}"})
    public AjaxResult viewUrl(@PathVariable(value = "workbookId") Long workbookId) {
        AjaxResult ajax = AjaxResult.success();
        ajax.put("viewUrl",tableauService.tableauUrl(workbookId));
        return ajax;
    }

    /**
     * tableau project 列表
     */
    @PreAuthorize("@ss.hasPermi('tableau:web:query')")
    @GetMapping("/projectList")
    public AjaxResult projectList() {
        AjaxResult ajax  = AjaxResult.success();
        List<Workbook> list = tableauService.allProject(getUserId());
        ajax.put("project",list);
        return ajax;
    }

    /**
     * tableau workbook 列表
     */
    @PreAuthorize("@ss.hasPermi('tableau:web:query')")
    @GetMapping("/workbookList/{projectId}")
    public AjaxResult workbookList(@PathVariable(value = "projectId") Long projectId) {
        AjaxResult ajax  = AjaxResult.success();
        Long u = getUserId();
        List<Workbook> list = tableauService.workbookByProjectId(getUserId(),projectId);
        ajax.put("workbook",list);
        return ajax;
    }

}
