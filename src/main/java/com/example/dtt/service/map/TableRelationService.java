package com.example.dtt.service.map;

import com.example.dtt.domain.entity.map.TableRelation;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 14:20
 * @describe
 */
public interface TableRelationService {
    /**
     * 根据表名获取表详情
     * @param tableName
     * @return
     */
    List<TableRelation> getTableRelation(String tableName);
}
