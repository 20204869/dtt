package com.example.dtt.datasource.common;

import com.example.dtt.datasource.common.datasource.DataSourceChannel;
import com.example.dtt.datasource.common.datasource.DataSourceChannelFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.lang.String.format;

public class DataSourcePluginManager {
    private static final Logger logger = LoggerFactory.getLogger(DataSourcePluginManager.class);

    private final Map<String, DataSourceChannel> datasourceClientMap = new ConcurrentHashMap<>();

    public Map<String, DataSourceChannel> getDataSourceChannelMap() {
        return Collections.unmodifiableMap(datasourceClientMap);
    }

    public void installPlugin() {
        final Set<String> names = new HashSet<>();

        ServiceLoader.load(DataSourceChannelFactory.class).forEach(factory -> {
            final String name = factory.getName();

            logger.info("Registering datasource plugin: {}", name);

            if (!names.add(name)) {
                throw new IllegalStateException(format("Duplicate datasource plugins named '%s'", name));
            }

            loadDatasourceClient(factory);

            logger.info("Registered datasource plugin: {}", name);
        });
    }

    private void loadDatasourceClient(DataSourceChannelFactory datasourceChannelFactory) {
        DataSourceChannel datasourceChannel = datasourceChannelFactory.create();
        datasourceClientMap.put(datasourceChannelFactory.getName(), datasourceChannel);
    }
}
