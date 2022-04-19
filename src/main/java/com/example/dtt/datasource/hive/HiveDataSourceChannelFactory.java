package com.example.dtt.datasource.hive;

import com.example.dtt.datasource.common.datasource.DataSourceChannel;
import com.example.dtt.datasource.common.datasource.DataSourceChannelFactory;
import com.google.auto.service.AutoService;

@AutoService(DataSourceChannelFactory.class)
public class HiveDataSourceChannelFactory implements DataSourceChannelFactory {
    @Override
    public String getName() {
        return "hive";
    }

    @Override
    public DataSourceChannel create() {
        return new HiveDataSourceChannel();
    }
}
