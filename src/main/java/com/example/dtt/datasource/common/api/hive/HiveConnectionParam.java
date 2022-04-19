package com.example.dtt.datasource.common.api.hive;

import com.example.dtt.datasource.common.datasource.BaseConnectionParam;

public class HiveConnectionParam extends BaseConnectionParam {
    @Override
    public String toString() {
        return "HiveConnectionParam{"
                + "user='" + user + '\''
                + ", password='" + password + '\''
                + ", address='" + address + '\''
                + ", database='" + database + '\''
                + ", jdbcUrl='" + jdbcUrl + '\''
                + ", driverLocation='" + driverLocation + '\''
                + ", driverClassName='" + driverClassName + '\''
                + ", validationQuery='" + validationQuery + '\''
                + ", other='" + other + '\''
                + '}';
    }
}
