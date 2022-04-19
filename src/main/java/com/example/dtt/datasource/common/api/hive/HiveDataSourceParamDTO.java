package com.example.dtt.datasource.common.api.hive;

import com.example.dtt.datasource.common.BaseDataSourceParamDTO;
import com.example.dtt.enums.DbType;

public class HiveDataSourceParamDTO extends BaseDataSourceParamDTO {

    @Override
    public String toString() {
        return "HiveDataSourceParamDTO{"
                + "host='" + host + '\''
                + ", port=" + port
                + ", database='" + database + '\''
                + ", userName='" + userName + '\''
                + ", password='" + password + '\''
                + ", other='" + other + '\''
                + '}';
    }

    @Override
    public DbType getType() {
        return DbType.HIVE;
    }
}
