package com.example.dtt.datasource.hive;

import com.example.dtt.constant.datasource.Constants;
import com.example.dtt.datasource.common.CommonDataSourceClient;
import com.example.dtt.datasource.common.JDBCDataSourceProvider;
import com.example.dtt.datasource.common.datasource.BaseConnectionParam;
import com.example.dtt.enums.DbType;
import com.example.dtt.utils.PropertyUtils;
import com.example.dtt.utils.StringUtils;
import com.example.dtt.utils.sql.CommonUtil;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.security.UserGroupInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.security.krb5.Config;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.example.dtt.constant.TaskConstants.*;

public class HiveDataSourceClient extends CommonDataSourceClient {

    private static final Logger logger = LoggerFactory.getLogger(HiveDataSourceClient.class);

    private ScheduledExecutorService kerberosRenewalService;

    private Configuration hadoopConf;
    protected HikariDataSource oneSessionDataSource;
    private UserGroupInformation ugi;

    public HiveDataSourceClient(BaseConnectionParam baseConnectionParam, DbType dbType) {
        super(baseConnectionParam, dbType);
    }

    @Override
    protected void preInit() {
        logger.info("PreInit in {}", getClass().getName());
        this.kerberosRenewalService = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    protected void initClient(BaseConnectionParam baseConnectionParam, DbType dbType) {
        logger.info("Create Configuration for hive configuration.");
        this.hadoopConf = createHadoopConf();
        logger.info("Create Configuration success.");

        logger.info("Create UserGroupInformation.");
        this.ugi = createUserGroupInformation(baseConnectionParam.getUser());
        logger.info("Create ugi success.");

        super.initClient(baseConnectionParam, dbType);
        this.oneSessionDataSource = JDBCDataSourceProvider.createOneSessionJdbcDataSource(baseConnectionParam, dbType);
        logger.info("Init {} success.", getClass().getName());
    }

    @Override
    protected void checkEnv(BaseConnectionParam baseConnectionParam) {
        super.checkEnv(baseConnectionParam);
    }


    private UserGroupInformation createUserGroupInformation(String username) {
        try {
            UserGroupInformation ugi = CommonUtil.createUGI(getHadoopConf(),username);
            try {
                Field isKeytabField = ugi.getClass().getDeclaredField("isKeytab");
                isKeytabField.setAccessible(true);
                isKeytabField.set(ugi, true);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                logger.warn(e.getMessage());
            }
            kerberosRenewalService.scheduleWithFixedDelay(() -> {
                try {
                    ugi.checkTGTAndReloginFromKeytab();
                } catch (IOException e) {
                    logger.error("Check TGT and Renewal from Keytab error", e);
                }
            }, 5, 5, TimeUnit.MINUTES);
            return ugi;
        } catch (IOException e) {
            throw new RuntimeException("createUserGroupInformation fail. ", e);
        }
    }

    protected Configuration createHadoopConf() {
        Configuration hadoopConf = new Configuration();
        hadoopConf.setBoolean("ipc.client.fallback-to-simple-auth-allowed", true);
        return hadoopConf;
    }

    protected Configuration getHadoopConf() {
        return this.hadoopConf;
    }

    @Override
    public Connection getConnection() {
        try {
            return oneSessionDataSource.getConnection();
        } catch (SQLException e) {
            logger.error("get oneSessionDataSource Connection fail SQLException: {}", e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void close() {
        super.close();

        logger.info("close HiveDataSourceClient.");
        kerberosRenewalService.shutdown();
        this.ugi = null;

        this.oneSessionDataSource.close();
        this.oneSessionDataSource = null;
    }
}
