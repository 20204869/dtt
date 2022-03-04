package com.example.dtt.domain.entity.map;

import com.example.dtt.domain.BaseEntity;

/**
 * @author reid
 * @date 2021/11/22 16:46
 * @describe 数据库对象
 */
public class DB extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long dbId;
    private String dbName;

    public DB() {
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Override
    public String toString() {
        return "DB{" +
                "dbId=" + dbId +
                ", dbName='" + dbName + '\'' +
                '}';
    }
}
