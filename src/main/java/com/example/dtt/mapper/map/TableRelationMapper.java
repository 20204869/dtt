package com.example.dtt.mapper.map;


import com.example.dtt.domain.entity.map.TableRelation;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 14:18
 * @describe 获取表业务逻辑及表血缘关系
 */
public interface TableRelationMapper {
    /**
     * 根据表名获取表详情
     * @param tableName
     * @return
     */
    TableRelation getTableRelationByName(String tableName);

    /**
     * 根据表名获取表详情
     * @param tableName
     * @return
     */
    TableRelation getTableRelationOriginName(String tableName);

    /**
     * 根据表ID 查询
     * @param tableId
     * @return
     */
    List<TableRelation> getTableRelationById(Long tableId);

}
