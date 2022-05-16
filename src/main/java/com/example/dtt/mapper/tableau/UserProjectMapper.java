package com.example.dtt.mapper.tableau;

import com.example.dtt.domain.system.SysUserProject;
import com.example.dtt.domain.system.SysUserTemplate;

import java.util.List;

/**
 * 数据源 映射器接口
 */
public interface UserProjectMapper {

    /**
     * 批量更新用户与tableau project 对应关系
     * @param projects
     * @return
     */
    int batchUserProject(List<SysUserProject> projects);

    /**
     * 删除用户与tableau project 之间的关系
     * @param projectId
     * @return
     */
    int deleteUserProjectById(Long projectId);

    int deleteUserProjectByIds(Long[] projectIdIds);

    /**
     * 根据用户id 删除tableau project 与用户的对应关系
     * @param userId
     * @return
     */
    int deleteUserProjectByUserId(Long userId);
}
