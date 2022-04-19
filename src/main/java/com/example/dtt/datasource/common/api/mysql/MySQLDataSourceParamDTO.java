package com.example.dtt.datasource.common.api.mysql;

import com.example.dtt.datasource.common.BaseDataSourceParamDTO;
import com.example.dtt.enums.DbType;

public class MySQLDataSourceParamDTO extends BaseDataSourceParamDTO {

    @Override
    public String toString() {
        return "MySQLDataSourceParamDTO{"
                + "name='" + name + '\''
                + ", note='" + note + '\''
                + ", host='" + host + '\''
                + ", port=" + port
                + ", database='" + database + '\''
                + ", userName='" + userName + '\''
                + ", password='" + password + '\''
                + ", other='" + other + '\''
                + '}';
    }

    @Override
    public DbType getType() {
        return DbType.MYSQL;
    }
}
