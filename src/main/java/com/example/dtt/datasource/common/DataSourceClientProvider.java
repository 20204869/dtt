package com.example.dtt.datasource.common;

import com.example.dtt.datasource.common.datasource.BaseConnectionParam;
import com.example.dtt.datasource.common.datasource.ConnectionParam;
import com.example.dtt.datasource.common.datasource.DataSourceChannel;
import com.example.dtt.datasource.common.datasource.DataSourceClient;
import com.example.dtt.enums.DbType;
import com.example.dtt.utils.sql.DataSourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataSourceClientProvider {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceClientProvider.class);

    private static final Map<String, DataSourceClient> uniqueId2dataSourceClientMap = new ConcurrentHashMap<>();

    private DataSourcePluginManager dataSourcePluginManager;

    private DataSourceClientProvider() {
        initDataSourcePlugin();
    }

    private static class DataSourceClientProviderHolder {
        private static final DataSourceClientProvider INSTANCE = new DataSourceClientProvider();
    }

    public static DataSourceClientProvider getInstance() {
        return DataSourceClientProviderHolder.INSTANCE;
    }

    public Connection getConnection(DbType dbType, ConnectionParam connectionParam) {
        BaseConnectionParam baseConnectionParam = (BaseConnectionParam) connectionParam;
        String datasourceUniqueId = DataSourceUtils.getDatasourceUniqueId(baseConnectionParam, dbType);
        logger.info("getConnection datasourceUniqueId {}", datasourceUniqueId);

        DataSourceClient dataSourceClient = uniqueId2dataSourceClientMap.computeIfAbsent(datasourceUniqueId, $ -> {
            Map<String, DataSourceChannel> dataSourceChannelMap = dataSourcePluginManager.getDataSourceChannelMap();
            DataSourceChannel dataSourceChannel = dataSourceChannelMap.get(dbType.getDescp());
            if (null == dataSourceChannel) {
                throw new RuntimeException(String.format("datasource plugin '%s' is not found", dbType.getDescp()));
            }
            return dataSourceChannel.createDataSourceClient(baseConnectionParam, dbType);
        });
        return dataSourceClient.getConnection();
    }

    private void initDataSourcePlugin() {
        dataSourcePluginManager = new DataSourcePluginManager();
        dataSourcePluginManager.installPlugin();
    }
}
