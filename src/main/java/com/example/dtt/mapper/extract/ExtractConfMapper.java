package com.example.dtt.mapper.extract;

import com.example.dtt.domain.entity.extract.ExtractConf;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 数据源 映射器接口
 */
public interface ExtractConfMapper{

    /**
     * 根据用户id 查询取数模板
     * @param userId
     * @return
     */
    List<ExtractConf> confListByUserId(@Param("templateName") String templateName, @Param("userId") Long userId);

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

    /**
     * 通过模板id查询用户
     * @param id
     * @return
     */
    List<Long> selectUserListById(Long id);

    /**
     * 根据用户id查询取数模板
     * @param userId
     * @return
     */
    List<ExtractConf> selectExtractConfByUserId(Long userId);

}
