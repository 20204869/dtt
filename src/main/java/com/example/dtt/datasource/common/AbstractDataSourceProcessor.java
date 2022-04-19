
package com.example.dtt.datasource.common;

import com.example.dtt.datasource.common.datasource.BaseConnectionParam;
import com.example.dtt.datasource.common.datasource.ConnectionParam;
import com.example.dtt.enums.DbType;
import org.apache.commons.collections4.MapUtils;

import java.text.MessageFormat;
import java.util.Map;
import java.util.regex.Pattern;

public abstract class AbstractDataSourceProcessor implements DataSourceProcessor {

    private static final Pattern IPV4_PATTERN = Pattern.compile("^[a-zA-Z0-9\\_\\-\\.\\,]+$");

    private static final Pattern IPV6_PATTERN = Pattern.compile("^[a-zA-Z0-9\\_\\-\\.\\:\\[\\]\\,]+$");

    private static final Pattern DATABASE_PATTER = Pattern.compile("^[a-zA-Z0-9\\_\\-\\.]+$");

    private static final Pattern PARAMS_PATTER = Pattern.compile("^[a-zA-Z0-9\\-\\_\\/\\@\\.]+$");

    @Override
    public void checkDatasourceParam(BaseDataSourceParamDTO baseDataSourceParamDTO) {
        checkHost(baseDataSourceParamDTO.getHost());
        checkDatasourcePatter(baseDataSourceParamDTO.getDatabase());
        checkOther(baseDataSourceParamDTO.getOther());
    }

    /**
     * Check the host is valid
     *
     * @param host datasource host
     */
    protected void checkHost(String host) {
        if (!IPV4_PATTERN.matcher(host).matches() || !IPV6_PATTERN.matcher(host).matches()) {
            throw new IllegalArgumentException("datasource host illegal");
        }
    }

    /**
     * check database name is valid
     *
     * @param database database name
     */
    protected void checkDatasourcePatter(String database) {
        if (!DATABASE_PATTER.matcher(database).matches()) {
            throw new IllegalArgumentException("datasource name illegal");
        }
    }

    /**
     * check other is valid
     *
     * @param other other
     */
    protected void checkOther(Map<String, String> other) {
        if (MapUtils.isEmpty(other)) {
            return;
        }
        boolean paramsCheck = other.entrySet().stream().allMatch(p -> PARAMS_PATTER.matcher(p.getValue()).matches());
        if (!paramsCheck) {
            throw new IllegalArgumentException("datasource other params illegal");
        }
    }

    @Override
    public String getDatasourceUniqueId(ConnectionParam connectionParam, DbType dbType) {
        BaseConnectionParam baseConnectionParam = (BaseConnectionParam) connectionParam;
        return MessageFormat.format("{0}@{1}@{2}", dbType.getDescp(), baseConnectionParam.getUser(), baseConnectionParam.getJdbcUrl());
    }
}
