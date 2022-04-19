package com.example.dtt.mapper.extract;

import com.example.dtt.domain.system.SysUserTemplate;

import java.util.List;

/**
 * 数据源 映射器接口
 */
public interface UserTemplateMapper {

    /**
     * 批量更新用户与取数模板对应关系
     * @param templates
     * @return
     */
    int batchUserTemplate(List<SysUserTemplate> templates);

    /**
     * 删除用户与取数模板关系
     * @param templateId
     * @return
     */
    int deleteUserTemplateById(Long templateId);

    int deleteUserTemplateByIds(Long[] templateIds);

    /**
     * 根据用户id 删除取数模板与用户的对应关系
     * @param userId
     * @return
     */
    int deleteUserTemplateByUserId(Long userId);
}
