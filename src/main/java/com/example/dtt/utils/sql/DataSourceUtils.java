package com.example.dtt.utils.sql;

import com.example.dtt.datasource.common.BaseDataSourceParamDTO;
import com.example.dtt.datasource.common.DataSourceProcessor;
import com.example.dtt.datasource.common.api.hive.HiveDataSourceProcessor;
import com.example.dtt.datasource.common.api.mysql.MySQLDataSourceProcessor;
import com.example.dtt.datasource.common.datasource.ConnectionParam;
import com.example.dtt.enums.DbType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;

public class DataSourceUtils {

    private DataSourceUtils() {
    }

    private static final Logger logger = LoggerFactory.getLogger(DataSourceUtils.class);

    private static final DataSourceProcessor mysqlProcessor = new MySQLDataSourceProcessor();
    private static final DataSourceProcessor hiveProcessor = new HiveDataSourceProcessor();

    /**
     * check datasource param
     *
     * @param baseDataSourceParamDTO datasource param
     */
    public static void checkDatasourceParam(BaseDataSourceParamDTO baseDataSourceParamDTO) {
        getDatasourceProcessor(baseDataSourceParamDTO.getType()).checkDatasourceParam(baseDataSourceParamDTO);
    }

    /**
     * build connection url
     *
     * @param baseDataSourceParamDTO datasourceParam
     */
    public static ConnectionParam buildConnectionParams(BaseDataSourceParamDTO baseDataSourceParamDTO) {
        ConnectionParam connectionParams = getDatasourceProcessor(baseDataSourceParamDTO.getType())
                .createConnectionParams(baseDataSourceParamDTO);
        if (logger.isDebugEnabled()) {
            logger.info("parameters map:{}", connectionParams);
        }
        return connectionParams;
    }

    public static ConnectionParam buildConnectionParams(DbType dbType, String connectionJson) {
        return getDatasourceProcessor(dbType).createConnectionParams(connectionJson);
    }

    public static String getJdbcUrl(DbType dbType, ConnectionParam baseConnectionParam) {
        return getDatasourceProcessor(dbType).getJdbcUrl(baseConnectionParam);
    }

    public static Connection getConnection(DbType dbType, ConnectionParam connectionParam) {
        try {
            return getDatasourceProcessor(dbType).getConnection(connectionParam);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getDatasourceDriver(DbType dbType) {
        return getDatasourceProcessor(dbType).getDatasourceDriver();
    }

    public static BaseDataSourceParamDTO buildDatasourceParamDTO(DbType dbType, String connectionParams) {
        return getDatasourceProcessor(dbType).createDatasourceParamDTO(connectionParams);
    }

    public static DataSourceProcessor getDatasourceProcessor(DbType dbType) {
        switch (dbType) {
            case MYSQL:
                return mysqlProcessor;
            case HIVE:
                return hiveProcessor;
            default:
                throw new IllegalArgumentException("datasource type illegal:" + dbType);
        }
    }

    /**
     * get datasource UniqueId
     */
    public static String getDatasourceUniqueId(ConnectionParam connectionParam, DbType dbType) {
        return getDatasourceProcessor(dbType).getDatasourceUniqueId(connectionParam, dbType);
    }
}
