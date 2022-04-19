package com.example.dtt.datasource.common.datasource;

import com.example.dtt.enums.DbType;

public interface DataSourceChannel {

    DataSourceClient createDataSourceClient(BaseConnectionParam baseConnectionParam, DbType dbType);
}
