package com.example.dtt.service.tableau;

import com.example.dtt.domain.entity.tableau.Views;
import com.example.dtt.domain.entity.tableau.Workbook;

import java.util.List;

/**
 * @author reid
 * @date 2022/4/25 14:26
 * @describe tableau 报表项目、视图处理服务
 */
public interface TableauService {
    /**
     * 查询所有的工作簿
     * @param workbook
     * @return
     */
    List<Workbook> projectList(Workbook workbook);

    /**
     * 查询所有tableau 项目
     *
     * @return
     */
    List<Workbook> projectAll();

    /**
     * 根据用户ID查询tableau workbook列表
     *
     * @param userId 用户ID
     * @return tableau workbook列表
     */
    List<Workbook> workbookByUserId(Long userId,Workbook workbook);

    /**
     * 通过id获取视图渲染url
     * @param workbookId
     * @return
     */
    String tableauUrl(Long workbookId);

    /**
     * 按用户展示tableau 项目
     * @param userId
     * @return
     */
    List<Workbook> allProject(Long userId);


    /**
     * 通过项目id和用户查询 tableau 项目下的workbook
     * @param userId
     * @param projectId
     * @return
     */
    List<Workbook> workbookByProjectId(Long userId,Long projectId);

    /**
     * 通过workbookID 查询 视图列表
     * @param workbookId
     * @return
     */
    List<Views> viewsByWorkbookId(Long workbookId);
}
