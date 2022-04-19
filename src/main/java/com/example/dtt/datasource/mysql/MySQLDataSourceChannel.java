package com.example.dtt.datasource.mysql;


import com.example.dtt.datasource.common.datasource.BaseConnectionParam;
import com.example.dtt.datasource.common.datasource.DataSourceChannel;
import com.example.dtt.datasource.common.datasource.DataSourceClient;
import com.example.dtt.enums.DbType;

public class MySQLDataSourceChannel implements DataSourceChannel {

    @Override
    public DataSourceClient createDataSourceClient(BaseConnectionParam baseConnectionParam, DbType dbType) {
        return new MySQLDataSourceClient(baseConnectionParam, dbType);
    }
}
