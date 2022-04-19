package com.example.dtt.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.google.common.base.Functions;

import java.util.Arrays;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public enum DbType {
    MYSQL(0, "mysql"),
    HIVE(1, "hive")
    ;

    @EnumValue
    private final int code;
    private final String descp;

    DbType(int code, String descp) {
        this.code = code;
        this.descp = descp;
    }

    public int getCode() {
        return code;
    }

    public String getDescp() {
        return descp;
    }

    private static final Map<Integer, DbType> DB_TYPE_MAP =
            Arrays.stream(DbType.values()).collect(toMap(DbType::getCode, Functions.identity()));

    public static DbType of(int type) {
        if (DB_TYPE_MAP.containsKey(type)) {
            return DB_TYPE_MAP.get(type);
        }
        return null;
    }

    public boolean isHive() {
        return this == DbType.HIVE;
    }
}
