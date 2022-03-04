package com.example.dtt.domain.entity.map;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author reid
 * @date 2021/11/22 16:48
 * @describe 表血缘关系及表业务逻辑
 */
public class TableRelation implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long tableId;
    private Long parentId;
    private String name;
    private String tableSql;

    private List<TableRelation> children = new ArrayList<>();

    public List<TableRelation> getChildren() {
        return children;
    }

    public void setChildren(List<TableRelation> children) {
        this.children = children;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTableSql() {
        return tableSql;
    }

    public void setTableSql(String tableSql) {
        this.tableSql = tableSql;
    }

    @Override
    public String toString() {
        return "TableRelation{" +
                "tableId=" + tableId +
                ", parentId=" + parentId +
                ", tableName='" + name + '\'' +
                ", tableSql='" + tableSql + '\'' +
                '}';
    }
}
