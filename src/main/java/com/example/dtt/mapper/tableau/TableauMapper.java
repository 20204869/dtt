package com.example.dtt.mapper.tableau;

import com.example.dtt.domain.entity.tableau.Views;
import com.example.dtt.domain.entity.tableau.Workbook;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * tableau 报表数据
 */
public interface TableauMapper {

    /**
     * 根据用户id 查询 tableau workbook列表
     * @param userId
     * @return
     */
    List<Workbook> workbookPermissionByUserId(Long userId);

    /**
     * 查询所有tableau 项目
     *
     * @return
     */
    List<Workbook> allProjects(Workbook workbook);

    /**
     * 按用户展示tableau 项目
     *
     * @param userId
     * @return
     */
    List<Workbook> allProject(Long userId);


    /**
     * 通过项目id和用户查询 tableau 项目下的workbook
     *
     * @param userId
     * @param projectId
     * @return
     */
    List<Workbook> workbookByProjectId(@Param("userId") Long userId, @Param("projectId") Long projectId);

    /**
     * 通过workbookID 查询 视图列表
     *
     * @param workbookId
     * @return
     */
    List<Views> viewsByWorkbookId(Long workbookId);


    /**
     * 通过视图id获取视图渲染url
     * @param viewId
     * @return
     */
    Views tableauUrl(Long viewId);

}
