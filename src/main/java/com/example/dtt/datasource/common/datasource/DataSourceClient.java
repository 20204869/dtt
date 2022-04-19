package com.example.dtt.datasource.common.datasource;

import java.sql.Connection;

public interface DataSourceClient {

    void checkClient();

    void close();

    Connection getConnection();
}
