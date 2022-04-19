package com.example.dtt.datasource.mysql;

import com.example.dtt.datasource.common.datasource.DataSourceChannel;
import com.example.dtt.datasource.common.datasource.DataSourceChannelFactory;
import com.google.auto.service.AutoService;
@AutoService(DataSourceChannelFactory.class)
public class MySQLDataSourceChannelFactory implements DataSourceChannelFactory {
    @Override
    public String getName() {
        return "mysql";
    }

    @Override
    public DataSourceChannel create() {
        return new MySQLDataSourceChannel();
    }
}
