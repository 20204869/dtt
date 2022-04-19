package com.example.dtt.datasource.common.datasource;

public interface DataSourceChannelFactory {
    /**
     * get datasource client
     */
    DataSourceChannel create();

    /**
     * get registry component name
     */
    String getName();
}
