package com.example.dtt.mapper.query;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.dtt.domain.entity.datasource.DataSource;
import com.example.dtt.domain.entity.query.HiveUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * hive-user 映射器接口
 */
public interface HiveUserMapper extends BaseMapper<HiveUser>{

    /**
     * hive与平台用户关系列表
     */
    List<HiveUser> hiveUserListPaging(HiveUser hiveUser);

    /**
     * 根据用户名查询关系列表
     * @return
     */
    HiveUser queryHiveUserByName(@Param("userName") String userName);

    /**
     * 根据id查询关系
     * @param id
     * @return
     */
    HiveUser queryHiveUserById(@Param("id") int id);

    /**
     * 根据id删除关系
     * @param id
     * @return
     */
    int deleteHiveUserById(@Param("id") Long id);

    /**
     * 更新关系信息
     * @return
     */
    int updateHiveUser(HiveUser hiveUser);

    /**
     * 批量删除关系
     * @param ids
     * @return
     */
    int deleteHiveUserByIds(Long[] ids);

    /**
     * 创建关系
     * @param hiveUser
     * @return
     */
    int createHiveUser(HiveUser hiveUser);
}
