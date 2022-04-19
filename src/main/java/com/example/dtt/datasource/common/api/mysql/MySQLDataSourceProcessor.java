package com.example.dtt.datasource.common.api.mysql;

import com.example.dtt.constant.datasource.Constants;
import com.example.dtt.datasource.common.AbstractDataSourceProcessor;
import com.example.dtt.datasource.common.BaseDataSourceParamDTO;
import com.example.dtt.datasource.common.datasource.BaseConnectionParam;
import com.example.dtt.datasource.common.datasource.ConnectionParam;
import com.example.dtt.enums.DbType;
import com.example.dtt.utils.JSONUtils;
import com.example.dtt.utils.StringUtils;
import com.example.dtt.utils.sql.PasswordUtils;
import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MySQLDataSourceProcessor extends AbstractDataSourceProcessor {

    private final Logger logger = LoggerFactory.getLogger(MySQLDataSourceProcessor.class);

    private static final String ALLOW_LOAD_LOCAL_IN_FILE_NAME = "allowLoadLocalInfile";

    private static final String AUTO_DESERIALIZE = "autoDeserialize";

    private static final String ALLOW_LOCAL_IN_FILE_NAME = "allowLocalInfile";

    private static final String ALLOW_URL_IN_LOCAL_IN_FILE_NAME = "allowUrlInLocalInfile";

    private static final String APPEND_PARAMS = "allowLoadLocalInfile=false&autoDeserialize=false&allowLocalInfile=false&allowUrlInLocalInfile=false";

    @Override
    public BaseDataSourceParamDTO createDatasourceParamDTO(String connectionJson) {
        MySQLConnectionParam connectionParams = (MySQLConnectionParam) createConnectionParams(connectionJson);
        MySQLDataSourceParamDTO mysqlDatasourceParamDTO = new MySQLDataSourceParamDTO();

        mysqlDatasourceParamDTO.setUserName(connectionParams.getUser());
        mysqlDatasourceParamDTO.setDatabase(connectionParams.getDatabase());
        mysqlDatasourceParamDTO.setOther(parseOther(connectionParams.getOther()));

        String address = connectionParams.getAddress();
        String[] hostSeperator = address.split(Constants.DOUBLE_SLASH);
        String[] hostPortArray = hostSeperator[hostSeperator.length - 1].split(Constants.COMMA);
        mysqlDatasourceParamDTO.setPort(Integer.parseInt(hostPortArray[0].split(Constants.COLON)[1]));
        mysqlDatasourceParamDTO.setHost(hostPortArray[0].split(Constants.COLON)[0]);

        return mysqlDatasourceParamDTO;
    }

    @Override
    public BaseConnectionParam createConnectionParams(BaseDataSourceParamDTO dataSourceParam) {
        MySQLDataSourceParamDTO mysqlDatasourceParam = (MySQLDataSourceParamDTO) dataSourceParam;
        String address = String.format("%s%s:%s", Constants.JDBC_MYSQL, mysqlDatasourceParam.getHost(), mysqlDatasourceParam.getPort());
        String jdbcUrl = String.format("%s/%s", address, mysqlDatasourceParam.getDatabase());

        MySQLConnectionParam mysqlConnectionParam = new MySQLConnectionParam();
        mysqlConnectionParam.setJdbcUrl(jdbcUrl);
        mysqlConnectionParam.setDatabase(mysqlDatasourceParam.getDatabase());
        mysqlConnectionParam.setAddress(address);
        mysqlConnectionParam.setUser(mysqlDatasourceParam.getUserName());
        mysqlConnectionParam.setPassword(PasswordUtils.encodePassword(mysqlDatasourceParam.getPassword()));
        mysqlConnectionParam.setDriverClassName(getDatasourceDriver());
        mysqlConnectionParam.setValidationQuery(getValidationQuery());
        mysqlConnectionParam.setOther(transformOther(mysqlDatasourceParam.getOther()));
        mysqlConnectionParam.setProps(mysqlDatasourceParam.getOther());

        return mysqlConnectionParam;
    }

    @Override
    public ConnectionParam createConnectionParams(String connectionJson) {
        return JSONUtils.parseObject(connectionJson, MySQLConnectionParam.class);
    }

    @Override
    public String getDatasourceDriver() {
        return Constants.COM_MYSQL_CJ_JDBC_DRIVER;
    }

    @Override
    public String getValidationQuery() {
        return Constants.MYSQL_VALIDATION_QUERY;
    }

    @Override
    public String getJdbcUrl(ConnectionParam connectionParam) {
        MySQLConnectionParam mysqlConnectionParam = (MySQLConnectionParam) connectionParam;
        String jdbcUrl = mysqlConnectionParam.getJdbcUrl();
        if (!StringUtils.isEmpty(mysqlConnectionParam.getOther())) {
            return String.format("%s?%s&%s", jdbcUrl, mysqlConnectionParam.getOther(), APPEND_PARAMS);
        }
        return String.format("%s?%s", jdbcUrl, APPEND_PARAMS);
    }

    @Override
    public Connection getConnection(ConnectionParam connectionParam) throws ClassNotFoundException, SQLException {
        MySQLConnectionParam mysqlConnectionParam = (MySQLConnectionParam) connectionParam;
        Class.forName(getDatasourceDriver());
        String user = mysqlConnectionParam.getUser();
        if (user.contains(AUTO_DESERIALIZE)) {
            logger.warn("sensitive param : {} in username field is filtered", AUTO_DESERIALIZE);
            user = user.replace(AUTO_DESERIALIZE, "");
        }
        String password = PasswordUtils.decodePassword(mysqlConnectionParam.getPassword());
        if (password.contains(AUTO_DESERIALIZE)) {
            logger.warn("sensitive param : {} in password field is filtered", AUTO_DESERIALIZE);
            password = password.replace(AUTO_DESERIALIZE, "");
        }
        return DriverManager.getConnection(getJdbcUrl(connectionParam), user, password);
    }

    @Override
    public DbType getDbType() {
        return DbType.MYSQL;
    }

    private String transformOther(Map<String, String> paramMap) {
        if (MapUtils.isEmpty(paramMap)) {
            return null;
        }
        Map<String, String> otherMap = new HashMap<>();
        paramMap.forEach((k, v) -> {
            if (!checkKeyIsLegitimate(k)) {
                return;
            }
            otherMap.put(k, v);
        });
        if (MapUtils.isEmpty(otherMap)) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        otherMap.forEach((key, value) -> stringBuilder.append(String.format("%s=%s&", key, value)));
        return stringBuilder.toString();
    }

    private static boolean checkKeyIsLegitimate(String key) {
        return !key.contains(ALLOW_LOAD_LOCAL_IN_FILE_NAME)
                && !key.contains(AUTO_DESERIALIZE)
                && !key.contains(ALLOW_LOCAL_IN_FILE_NAME)
                && !key.contains(ALLOW_URL_IN_LOCAL_IN_FILE_NAME);
    }

    private Map<String, String> parseOther(String other) {
        if (StringUtils.isEmpty(other)) {
            return null;
        }
        Map<String, String> otherMap = new LinkedHashMap<>();
        for (String config : other.split("&")) {
            otherMap.put(config.split("=")[0], config.split("=")[1]);
        }
        return otherMap;
    }

}
