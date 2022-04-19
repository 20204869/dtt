package com.example.dtt.service.datasource.impl;

import com.example.dtt.constant.datasource.Constants;
import com.example.dtt.constant.datasource.Status;
import com.example.dtt.datasource.common.BaseDataSourceParamDTO;
import com.example.dtt.datasource.common.DataSourceClientProvider;
import com.example.dtt.datasource.common.datasource.BaseConnectionParam;
import com.example.dtt.datasource.common.datasource.ConnectionParam;
import com.example.dtt.domain.AjaxResult;
import com.example.dtt.domain.entity.datasource.DataSource;
import com.example.dtt.domain.entity.system.SysUser;
import com.example.dtt.enums.DbType;
import com.example.dtt.exception.GlobalException;
import com.example.dtt.mapper.datasource.DataSourceMapper;
import com.example.dtt.service.datasource.DataSourceService;
import com.example.dtt.utils.JSONUtils;
import com.example.dtt.utils.StringUtils;
import com.example.dtt.utils.sql.DataSourceUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author reid
 * @date 2022/3/23 18:02
 * @describe
 */
@Service
public class DataSourceServiceImpl implements DataSourceService {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceServiceImpl.class);

    @Autowired
    private DataSourceMapper mapper;

    /**
     * 创建数据源
     *
     * @param userName
     * @param dataSourceParam
     * @return
     */
    @Override
    public AjaxResult createDataSource(String userName, BaseDataSourceParamDTO dataSourceParam) {
        DataSourceUtils.checkDatasourceParam(dataSourceParam);
        // check name can use or not
        if (checkName(dataSourceParam.getName())) {
            return AjaxResult.error(Status.DATASOURCE_EXIST.getMsg());
        }
        // check connect
        ConnectionParam connectionParam = DataSourceUtils.buildConnectionParams(dataSourceParam);
        AjaxResult isConnection = checkConnection(dataSourceParam.getType(), connectionParam);
        if (Status.SUCCESS.getCode() != (Integer) isConnection.get("code")) {
            return AjaxResult.error(Status.DATASOURCE_CONNECT_FAILED.getMsg());
        }
        // build datasource
        DataSource dataSource = new DataSource();

        dataSource.setName(dataSourceParam.getName().trim());
        dataSource.setRemark(dataSourceParam.getNote());
        dataSource.setCreateBy(userName);
        dataSource.setType(dataSourceParam.getType());
        dataSource.setConnectionParams(JSONUtils.toJsonString(connectionParam));
        try {
            return AjaxResult.success(mapper.createDataSource(dataSource));
        } catch (DuplicateKeyException ex) {
            logger.error("Create datasource error.", ex);
            return AjaxResult.error(Status.DATASOURCE_EXIST.getMsg());
        }
    }

    @Override
    public AjaxResult checkConnection(DbType type, ConnectionParam connectionParam) {
        try (Connection connection = DataSourceClientProvider.getInstance().getConnection(type, connectionParam)) {
            if (connection == null) {
                return AjaxResult.error(Status.CONNECTION_TEST_FAILURE.getCode(), Status.CONNECTION_TEST_FAILURE.getMsg());
            }
            return new AjaxResult(Status.SUCCESS.getCode(), Status.SUCCESS.getMsg());
        } catch (Exception e) {
            logger.error("datasource test connection error, dbType:{}, connectionParam:{}, message:{}.", type, connectionParam, e.getMessage());
            return AjaxResult.error(Status.CONNECTION_TEST_FAILURE.getCode(), e.getMessage());
        }
    }

    //检查数据源是否已存在
    private boolean checkName(String name) {
        List<DataSource> queryDataSource = mapper.queryDataSourceByName(name.trim());
        return queryDataSource != null && !queryDataSource.isEmpty();
    }

    /**
     * 修改数据源
     *
     * @param id
     * @param dataSourceParam
     * @param userName
     * @return
     */
    @Override
    public int updateDataSource(int id, BaseDataSourceParamDTO dataSourceParam, String userName) {
        DataSourceUtils.checkDatasourceParam(dataSourceParam);
        // determine whether the data source exists
        DataSource dataSource = mapper.queryDataSourceById(id);
        if (dataSource == null){
            return Status.RESOURCE_NOT_EXIST.getCode();
        }
        //check name can use or not
        if (!dataSource.getName().trim().equals(dataSource.getName()) && checkName(dataSource.getName())) {
            return Status.DATASOURCE_EXIST.getCode();
        }
        //check password，if the password is not updated, set to the old password.
        BaseConnectionParam connectionParam = (BaseConnectionParam) DataSourceUtils.buildConnectionParams(dataSourceParam);
        String password = connectionParam.getPassword();
        if (StringUtils.isBlank(password)) {
            String oldConnectionParams = dataSource.getConnectionParams();
            ObjectNode oldParams = JSONUtils.parseObject(oldConnectionParams);
            connectionParam.setPassword(oldParams.path(Constants.PASSWORD).asText());
        }

        AjaxResult isConnection = checkConnection(dataSource.getType(), connectionParam);
        if (isConnection.isFailed()) {
            return (int)isConnection.get("code");
        }

        dataSource.setName(dataSourceParam.getName().trim());
        dataSource.setRemark(dataSourceParam.getNote());
        dataSource.setUpdateBy(userName);
        dataSource.setType(dataSource.getType());
        dataSource.setConnectionParams(JSONUtils.toJsonString(connectionParam));
        try {
            return mapper.updateDataSource(dataSource);
        } catch (DuplicateKeyException ex) {
            logger.error("Update datasource error.", ex);
            return Status.DATASOURCE_EXIST.getCode();
        }
    }

    /**
     * 根据数据源查询详情
     *
     * @param id
     * @return
     */
    @Override
    public AjaxResult queryDataSource(int id) {
        AjaxResult ajax = AjaxResult.success();
        Map<String, Object> result = new HashMap<>();
        DataSource dataSource = mapper.queryDataSourceById(id);
        if (dataSource == null) {
            return AjaxResult.error(Status.RESOURCE_NOT_EXIST.getMsg());
        }
        // type
        BaseDataSourceParamDTO baseDataSourceParamDTO = DataSourceUtils.buildDatasourceParamDTO(
                dataSource.getType(), dataSource.getConnectionParams());
        baseDataSourceParamDTO.setId(dataSource.getId());
        baseDataSourceParamDTO.setName(dataSource.getName());
        baseDataSourceParamDTO.setNote(dataSource.getRemark());

        result.put(Constants.DATA_LIST, baseDataSourceParamDTO);
        ajax.put(Constants.DATA_LIST, baseDataSourceParamDTO);
        return ajax;
    }

    @Override
    public List<DataSource> queryDataSourceListPaging(DataSource source) {
        List<DataSource> dataSources = mapper.queryDataSourceListPaging(source);
        handlePasswd(dataSources);
        return dataSources;
    }

    /**
     * handle datasource connection password for safety
     */
    private void handlePasswd(List<DataSource> dataSourceList) {
        for (DataSource dataSource : dataSourceList) {
            String connectionParams = dataSource.getConnectionParams();
            ObjectNode object = JSONUtils.parseObject(connectionParams);
            object.put(Constants.PASSWORD, getHiddenPassword());
            dataSource.setConnectionParams(object.toString());
        }
    }

    private String getHiddenPassword() {
        return Constants.XXXXXX;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int deleteDataSourceById(Long datasourceId) {
        return mapper.deleteDataSourceById(datasourceId);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int deleteDataSourceByIds(Long[] ids) {
        return mapper.deleteDataSourceByIds(ids);
    }
}
