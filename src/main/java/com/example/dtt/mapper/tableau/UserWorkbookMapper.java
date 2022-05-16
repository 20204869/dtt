package com.example.dtt.mapper.tableau;

import com.example.dtt.domain.system.SysUserWorkbook;

import java.util.List;

/**
 * 数据源 映射器接口
 */
public interface UserWorkbookMapper {

    /**
     * 批量更新用户与tableau workbook 对应关系
     * @param workbooks
     * @return
     */
    int batchUserWorkbook(List<SysUserWorkbook> workbooks);

    /**
     * 删除用户与tableau workbook对应关系
     * @param workbookId
     * @return
     */
    int deleteUserWorkbookById(Long workbookId);

    int deleteUserWorkbookByIds(Long[] workbookIds);

    /**
     * 根据用户id 删除取数模板与用户的对应关系
     * @param userId
     * @return
     */
    int deleteUserWorkbookByUserId(Long userId);
}
