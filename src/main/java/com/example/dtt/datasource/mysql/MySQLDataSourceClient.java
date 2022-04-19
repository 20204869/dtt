package com.example.dtt.datasource.mysql;

import com.example.dtt.datasource.common.CommonDataSourceClient;
import com.example.dtt.datasource.common.datasource.BaseConnectionParam;
import com.example.dtt.enums.DbType;

public class MySQLDataSourceClient extends CommonDataSourceClient {

    public MySQLDataSourceClient(BaseConnectionParam baseConnectionParam, DbType dbType) {
        super(baseConnectionParam, dbType);
    }

}
