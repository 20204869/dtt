package com.example.dtt.datasource.common.api.hive;

import com.example.dtt.constant.datasource.Constants;
import com.example.dtt.datasource.common.AbstractDataSourceProcessor;
import com.example.dtt.datasource.common.BaseDataSourceParamDTO;
import com.example.dtt.datasource.common.datasource.BaseConnectionParam;
import com.example.dtt.datasource.common.datasource.ConnectionParam;
import com.example.dtt.enums.DbType;
import com.example.dtt.utils.JSONUtils;
import com.example.dtt.utils.StringUtils;
import com.example.dtt.utils.sql.CommonUtils;
import com.example.dtt.utils.sql.PasswordUtils;
import org.apache.commons.collections4.MapUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class HiveDataSourceProcessor extends AbstractDataSourceProcessor {

    @Override
    public BaseDataSourceParamDTO createDatasourceParamDTO(String connectionJson) {
        HiveDataSourceParamDTO hiveDataSourceParamDTO = new HiveDataSourceParamDTO();
        HiveConnectionParam hiveConnectionParam = (HiveConnectionParam) createConnectionParams(connectionJson);

        hiveDataSourceParamDTO.setDatabase(hiveConnectionParam.getDatabase());
        hiveDataSourceParamDTO.setUserName(hiveConnectionParam.getUser());
        hiveDataSourceParamDTO.setOther(parseOther(hiveConnectionParam.getOther()));

        String[] tmpArray = hiveConnectionParam.getAddress().split(Constants.DOUBLE_SLASH);
        StringBuilder hosts = new StringBuilder();
        String[] hostPortArray = tmpArray[tmpArray.length - 1].split(Constants.COMMA);
        for (String hostPort : hostPortArray) {
            hosts.append(hostPort.split(Constants.COLON)[0]).append(Constants.COMMA);
        }
        hosts.deleteCharAt(hosts.length() - 1);
        hiveDataSourceParamDTO.setHost(hosts.toString());
        hiveDataSourceParamDTO.setPort(Integer.parseInt(hostPortArray[0].split(Constants.COLON)[1]));

        return hiveDataSourceParamDTO;
    }

    @Override
    public BaseConnectionParam createConnectionParams(BaseDataSourceParamDTO datasourceParam) {
        HiveDataSourceParamDTO hiveParam = (HiveDataSourceParamDTO) datasourceParam;
        StringBuilder address = new StringBuilder();
        address.append(Constants.JDBC_HIVE_2);
        for (String zkHost : hiveParam.getHost().split(",")) {
            address.append(String.format("%s:%s,", zkHost, hiveParam.getPort()));
        }
        address.deleteCharAt(address.length() - 1);
        String jdbcUrl = address.toString() + "/" + hiveParam.getDatabase();

        HiveConnectionParam hiveConnectionParam = new HiveConnectionParam();
        hiveConnectionParam.setDatabase(hiveParam.getDatabase());
        hiveConnectionParam.setAddress(address.toString());
        hiveConnectionParam.setJdbcUrl(jdbcUrl);
        hiveConnectionParam.setUser(hiveParam.getUserName());
        hiveConnectionParam.setPassword(PasswordUtils.encodePassword(hiveParam.getPassword()));
        hiveConnectionParam.setDriverClassName(getDatasourceDriver());
        hiveConnectionParam.setValidationQuery(getValidationQuery());

        hiveConnectionParam.setOther(transformOther(hiveParam.getOther()));
        hiveConnectionParam.setProps(hiveParam.getOther());
        return hiveConnectionParam;
    }

    @Override
    public ConnectionParam createConnectionParams(String connectionJson) {
        return JSONUtils.parseObject(connectionJson, HiveConnectionParam.class);
    }

    @Override
    public String getDatasourceDriver() {
        return Constants.ORG_APACHE_HIVE_JDBC_HIVE_DRIVER;
    }

    @Override
    public String getValidationQuery() {
        return Constants.HIVE_VALIDATION_QUERY;
    }

    @Override
    public String getJdbcUrl(ConnectionParam connectionParam) {
        HiveConnectionParam hiveConnectionParam = (HiveConnectionParam) connectionParam;
        String jdbcUrl = hiveConnectionParam.getJdbcUrl();
        String otherParams = filterOther(hiveConnectionParam.getOther());
        if (StringUtils.isNotEmpty(otherParams) && !"?".equals(otherParams.substring(0, 1))) {
            jdbcUrl += ";";
        }
        return jdbcUrl + otherParams;
    }

    @Override
    public Connection getConnection(ConnectionParam connectionParam) throws IOException, ClassNotFoundException, SQLException {
        HiveConnectionParam hiveConnectionParam = (HiveConnectionParam) connectionParam;
        Class.forName(getDatasourceDriver());
        return DriverManager.getConnection(getJdbcUrl(connectionParam),
                hiveConnectionParam.getUser(), PasswordUtils.decodePassword(hiveConnectionParam.getPassword()));
    }

    @Override
    public DbType getDbType() {
        return DbType.HIVE;
    }

    private String transformOther(Map<String, String> otherMap) {
        if (MapUtils.isEmpty(otherMap)) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        otherMap.forEach((key, value) -> stringBuilder.append(String.format("%s=%s;", key, value)));
        return stringBuilder.toString();
    }

    private String filterOther(String otherParams) {
        if (StringUtils.isBlank(otherParams)) {
            return "";
        }

        StringBuilder hiveConfListSb = new StringBuilder();
        hiveConfListSb.append("?");
        StringBuilder sessionVarListSb = new StringBuilder();

        String[] otherArray = otherParams.split(";", -1);

        for (String conf : otherArray) {
            sessionVarListSb.append(conf).append(";");
        }

        // remove the last ";"
        if (sessionVarListSb.length() > 0) {
            sessionVarListSb.deleteCharAt(sessionVarListSb.length() - 1);
        }

        if (hiveConfListSb.length() > 0) {
            hiveConfListSb.deleteCharAt(hiveConfListSb.length() - 1);
        }

        return sessionVarListSb.toString() + hiveConfListSb.toString();
    }

    private Map<String, String> parseOther(String other) {
        if (other == null) {
            return null;
        }
        Map<String, String> otherMap = new LinkedHashMap<>();
        String[] configs = other.split(";");
        for (String config : configs) {
            otherMap.put(config.split("=")[0], config.split("=")[1]);
        }
        return otherMap;
    }
}
