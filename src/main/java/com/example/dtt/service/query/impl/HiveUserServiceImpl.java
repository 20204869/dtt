package com.example.dtt.service.query.impl;

import com.example.dtt.constant.Status;
import com.example.dtt.constant.datasource.Constants;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.entity.query.HiveUser;
import com.example.dtt.mapper.query.HiveUserMapper;
import com.example.dtt.service.query.HiveUserService;
import com.example.dtt.utils.JSONUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author reid
 * @date 2022/3/23 18:02
 * @describe
 */
@Service
public class HiveUserServiceImpl implements HiveUserService {

    private static final Logger logger = LoggerFactory.getLogger(HiveUserServiceImpl.class);

    @Autowired
    private HiveUserMapper mapper;

    /**
     * 创建关系配置
     * @return
     */
    @Override
    public AjaxResult createHiveUser(HiveUser hiveUser) {
        //检查用户是否已配置
        HiveUser hive = mapper.queryHiveUserByName(hiveUser.getUserName());
        if (hive != null){
            return AjaxResult.error("用户已配置！");
        }
        return AjaxResult.success(mapper.createHiveUser(hiveUser));
    }
    /**
     * 修改关系
     *
     * @param id
     * @param userName
     * @return
     */
    @Override
    public int updateHiveUser(int id, HiveUser hiveUser, String userName) {
        HiveUser user = mapper.queryHiveUserById(id);
        if (user == null){
            return Status.USER_NOT_EXIST.getCode();
        }
        hiveUser.setUserName(hiveUser.getUserName().trim());
        hiveUser.setRemark(hiveUser.getRemark());
        hiveUser.setUpdateBy(userName);
        hiveUser.setConnectionParams(hiveUser.getConnectionParams());
        return mapper.updateHiveUser(hiveUser);
    }

    /**
     * 根据数据源查询详情
     *
     * @param id
     * @return
     */
    @Override
    public AjaxResult queryHiveUser(int id) {
        AjaxResult ajax = AjaxResult.success();
        HiveUser hiveUser = mapper.queryHiveUserById(id);
        if (hiveUser == null) {
            return AjaxResult.error(Status.USER_NOT_EXIST.getMsg());
        }
        ajax.put(Constants.DATA_LIST, hiveUser);
        return ajax;
    }

    @Override
    public List<HiveUser> hiveUserListPaging(HiveUser hiveUser) {
        List<HiveUser> hiveUsers = mapper.hiveUserListPaging(hiveUser);
        handlePasswd(hiveUsers);
        return hiveUsers;
    }

    /**
     * handle datasource connection password for safety
     */
    private void handlePasswd(List<HiveUser> hiveUsers) {
        for (HiveUser hiveUser : hiveUsers) {
            String connectionParams = hiveUser.getConnectionParams();
            ObjectNode object = JSONUtils.parseObject(connectionParams);
            object.put(Constants.PASSWORD, getHiddenPassword());
            hiveUser.setConnectionParams(object.toString());
        }
    }

    private String getHiddenPassword() {
        return Constants.XXXXXX;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int deleteHiveUserById(Long hiveUserId) {
        return mapper.deleteHiveUserById(hiveUserId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int deleteHiveUserByIds(Long[] ids) {
        return mapper.deleteHiveUserByIds(ids);
    }
}
