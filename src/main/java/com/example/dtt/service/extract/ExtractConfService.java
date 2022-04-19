package com.example.dtt.service.extract;

import com.example.dtt.domain.entity.extract.ExtractConf;
import com.example.dtt.domain.entity.system.SysRole;

import java.util.List;

/**
 * @author reid
 * @date 2022/3/27 16:32
 * @describe 取数模板 服务层
 */
public interface ExtractConfService {

    /**
     * 查询所有取数模板
     *
     * @return 模板列表
     */
    List<ExtractConf> selectExtractConfAll();

    /**
     * 根据用户ID查询取数模板列表
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    List<ExtractConf> extractConfByUserId(Long userId);

    /**
     * 根据用户id 查询取数模板
     * @param userId
     * @return
     */
    List<ExtractConf> confListByUserId(String templateName,Long userId);
    /**
     * 通过模板id 查询用户
     * @param id
     * @return
     */
    List<Long> selectUserListById(Long id);

    /**
     * 分页以及根据条件查询配置信息
     * @param conf
     * @return
     */
    List<ExtractConf> confListPaging(ExtractConf conf);

    /**
     * 新增模板
     * @param conf
     * @return
     */
    int saveConf(ExtractConf conf);

    /**
     * 更新模板
     * @param conf
     * @return
     */
    int updateConf(ExtractConf conf);

    /**
     * 通过id查询配置信息
     * @param id
     * @return
     */
    ExtractConf queryConfById(Long id);


    /**
     * 取数模板分配用户
     */
    void insertUserTemplate(Long id, Long[] userIds);

    /**
     * 通过name查询配置信息用于校验不允许有重复模板名
     * @param name
     * @return
     */
    ExtractConf queryConfByName(String name);

    /**
     * 通过id删除模板
     * @param id
     * @return
     */
    int deleteConfById(Long id);

    /**
     * 批量删除模板
     * @param ids
     * @return
     */
    int deleteConfByIds(Long[] ids);

}
