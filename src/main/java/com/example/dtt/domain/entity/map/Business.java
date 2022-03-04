package com.example.dtt.domain.entity.map;


import com.example.dtt.domain.BaseEntity;

import java.util.Objects;

/**
 * @author reid
 * @date 2021/11/22 16:48
 * @describe 业务系统表
 */
public class Business extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 业务表名
     */
    private String tableName;
    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 字段名
     */
    private String colName;
    /**
     * 字段注释
     */
    private String colComment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Business business = (Business) o;
        return tableName.equals(business.tableName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName);
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColComment() {
        return colComment;
    }

    public void setColComment(String colComment) {
        this.colComment = colComment;
    }

    @Override
    public String toString() {
        return "Business{" +
                "tableName='" + tableName + '\'' +
                ", tableComment='" + tableComment + '\'' +
                ", colName='" + colName + '\'' +
                ", colComment='" + colComment + '\'' +
                '}';
    }
}
