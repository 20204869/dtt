package com.example.dtt.datasource.common.api.mysql;


import com.example.dtt.datasource.common.datasource.BaseConnectionParam;

public class MySQLConnectionParam extends BaseConnectionParam {
    @Override
    public String toString() {
        return "MySQLConnectionParam{"
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
