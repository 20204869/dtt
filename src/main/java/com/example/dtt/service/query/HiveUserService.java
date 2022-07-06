package com.example.dtt.service.query;

import com.example.dtt.datasource.common.BaseDataSourceParamDTO;
import com.example.dtt.datasource.common.datasource.ConnectionParam;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.entity.datasource.DataSource;
import com.example.dtt.domain.entity.query.HiveUser;
import com.example.dtt.enums.DbType;

import java.util.List;

/**
 * hive-平台用户 服务层接口
 */
public interface HiveUserService {

    /**
     * 创建hive-user关系
     * @return
     */
    AjaxResult createHiveUser(HiveUser hiveUser);

    /**
     * 更新关系
     * @return
     */
    int updateHiveUser(int id,HiveUser hiveUser,String userName);

    /**
     * 获取关系详情
     * @param id
     * @return
     */
    AjaxResult queryHiveUser(int id);

    /**
     * hive-user 列表
     */
    List<HiveUser> hiveUserListPaging(HiveUser hiveUser);

    /**
     * delete 关系
     *
     * @param hiveUserId
     * @return delete result code
     */
    int deleteHiveUserById(Long hiveUserId);

    /**
     * 批量删除hive-user
     * @param ids
     * @return
     */
    int deleteHiveUserByIds(Long[] ids);

}
