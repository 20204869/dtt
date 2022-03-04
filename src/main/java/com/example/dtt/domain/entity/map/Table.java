package com.example.dtt.domain.entity.map;


import com.example.dtt.domain.BaseEntity;

import java.util.Objects;

/**
 * @author reid
 * @date 2021/11/22 16:48
 * @describe 数据库表对象
 */
public class Table extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long tableId;
    private String tableName;
    private Long dbId;

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getDbId() {
        return dbId;
    }

    public void setDbId(Long dbId) {
        this.dbId = dbId;
    }

    public Table() {
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return tableId.equals(table.tableId) && tableName.equals(table.tableName) && dbId.equals(table.dbId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableId, tableName, dbId);
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableId=" + tableId +
                ", tableName='" + tableName + '\'' +
                ", dbId=" + dbId +
                '}';
    }
}
