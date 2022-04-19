package com.example.dtt.datasource.common;


import com.example.dtt.datasource.common.datasource.ConnectionParam;
import com.example.dtt.enums.DbType;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface DataSourceProcessor {

    /**
     * 检查参数是否可用
     */
    void checkDatasourceParam(BaseDataSourceParamDTO datasourceParam);

    /**
     * 获取数据源的唯一ID
     */
    String getDatasourceUniqueId(ConnectionParam connectionParam, DbType dbType);

    /**
     * 通过连接参数创建连接信息
     */
    BaseDataSourceParamDTO createDatasourceParamDTO(String connectionJson);

    /**
     * 通过创建的连接参数创建连接对象
     */
    ConnectionParam createConnectionParams(BaseDataSourceParamDTO datasourceParam);

    /**
     * 序列化连接信息
     */
    ConnectionParam createConnectionParams(String connectionJson);

    /**
     * 获取数据源驱动
     */
    String getDatasourceDriver();

    /**
     * 获取可用查询
     */
    String getValidationQuery();

    String getJdbcUrl(ConnectionParam connectionParam);


    Connection getConnection(ConnectionParam connectionParam) throws ClassNotFoundException, SQLException, IOException;

    /**
     * 获取数据源类型
     * @return
     */
    DbType getDbType();
}
