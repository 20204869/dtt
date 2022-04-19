package com.example.dtt.datasource.common;

import com.example.dtt.constant.datasource.Constants;
import com.example.dtt.datasource.common.datasource.BaseConnectionParam;
import com.example.dtt.enums.DbType;
import com.example.dtt.utils.PropertyUtils;
import com.example.dtt.utils.StringUtils;
import com.example.dtt.utils.sql.DataSourceUtils;
import com.example.dtt.utils.sql.PasswordUtils;
import com.zaxxer.hikari.HikariDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Driver;

/**
 * Jdbc Data Source Provider
 */
public class JDBCDataSourceProvider {

    private static final Logger logger = LoggerFactory.getLogger(JDBCDataSourceProvider.class);

    public static HikariDataSource createJdbcDataSource(BaseConnectionParam properties, DbType dbType) {
        logger.info("Creating HikariDataSource pool for maxActive:{}", PropertyUtils.getInt(Constants.SPRING_DATASOURCE_MAX_ACTIVE, 50));
        HikariDataSource dataSource = new HikariDataSource();

        //TODO Support multiple versions of data sources
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        loaderJdbcDriver(classLoader, properties, dbType);

        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setJdbcUrl(DataSourceUtils.getJdbcUrl(dbType, properties));
        dataSource.setUsername(properties.getUser());
        dataSource.setPassword(PasswordUtils.decodePassword(properties.getPassword()));

        dataSource.setMinimumIdle(PropertyUtils.getInt(Constants.SPRING_DATASOURCE_MIN_IDLE, 5));
        dataSource.setMaximumPoolSize(PropertyUtils.getInt(Constants.SPRING_DATASOURCE_MAX_ACTIVE, 50));
        dataSource.setConnectionTestQuery(properties.getValidationQuery());

        if (properties.getProps() != null) {
            properties.getProps().forEach(dataSource::addDataSourceProperty);
        }

        logger.info("Creating HikariDataSource pool success.");
        return dataSource;
    }

    /**
     * @return One Session Jdbc DataSource
     */
    public static HikariDataSource createOneSessionJdbcDataSource(BaseConnectionParam properties, DbType dbType) {
        logger.info("Creating OneSession HikariDataSource pool for maxActive:{}", PropertyUtils.getInt(Constants.SPRING_DATASOURCE_MAX_ACTIVE, 50));

        HikariDataSource dataSource = new HikariDataSource();

        dataSource.setDriverClassName(properties.getDriverClassName());
        dataSource.setJdbcUrl(DataSourceUtils.getJdbcUrl(dbType, properties));
        dataSource.setUsername(properties.getUser());
        dataSource.setPassword(PasswordUtils.decodePassword(properties.getPassword()));

        Boolean isOneSession = PropertyUtils.getBoolean(Constants.SUPPORT_HIVE_ONE_SESSION, false);
        dataSource.setMinimumIdle(isOneSession ? 1 : PropertyUtils.getInt(Constants.SPRING_DATASOURCE_MIN_IDLE, 5));
        dataSource.setMaximumPoolSize(isOneSession ? 1 : PropertyUtils.getInt(Constants.SPRING_DATASOURCE_MAX_ACTIVE, 50));
        dataSource.setConnectionTestQuery(properties.getValidationQuery());

        if (properties.getProps() != null) {
            properties.getProps().forEach(dataSource::addDataSourceProperty);
        }

        logger.info("Creating OneSession HikariDataSource pool success.");
        return dataSource;
    }

    protected static void loaderJdbcDriver(ClassLoader classLoader, BaseConnectionParam properties, DbType dbType) {
        String drv = StringUtils.isBlank(properties.getDriverClassName()) ? DataSourceUtils.getDatasourceProcessor(dbType).getDatasourceDriver() : properties.getDriverClassName();
        try {
            final Class<?> clazz = Class.forName(drv, true, classLoader);
            final Driver driver = (Driver) clazz.newInstance();
            if (!driver.acceptsURL(properties.getJdbcUrl())) {
                logger.warn("Jdbc driver loading error. Driver {} cannot accept url.", drv);
                throw new RuntimeException("Jdbc driver loading error.");
            }
            if (dbType.equals(DbType.MYSQL)) {
                if (driver.getMajorVersion() >= 8) {
                    properties.setDriverClassName(drv);
                } else {
                    properties.setDriverClassName(Constants.COM_MYSQL_JDBC_DRIVER);
                }
            }
           if (dbType.equals(DbType.HIVE)){
               properties.setDriverClassName(drv);
           }
        } catch (final Exception e) {
            logger.warn("The specified driver not suitable.");
        }
    }

}
