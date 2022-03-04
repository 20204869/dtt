package com.example.dtt.service.map;

import com.example.dtt.domain.entity.map.Table;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 14:20
 * @describe
 */
public interface TableService {
    /**
     * 根据条件查询表
     * @param table
     * @return
     */
    List<Table> selectTableList(Table table);

    /**
     * 根据表名查询表
     * @param tableName
     * @return
     */
    List<Table> selectTableByTableName(String tableName);

    /**
     * 根据表id查询表
     * @param tableId
     * @return
     */
    Table selectByTableId(Long tableId);
}
