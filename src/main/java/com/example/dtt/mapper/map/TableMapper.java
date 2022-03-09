package com.example.dtt.mapper.map;


import com.example.dtt.domain.entity.map.Table;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 14:18
 * @describe 获取表信息 数据层
 */
public interface TableMapper {
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
     * 库表树形结构
     * @return
     */
    List<Table> dbTableList ();

    /**
     * 根据表id查询表
     * @param tableId
     * @return
     */
    Table selectByTableId(Long tableId);
}
