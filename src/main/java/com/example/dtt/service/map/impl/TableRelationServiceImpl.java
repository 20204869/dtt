package com.example.dtt.service.map.impl;

import com.example.dtt.domain.entity.map.TableRelation;
import com.example.dtt.mapper.map.TableRelationMapper;
import com.example.dtt.service.map.TableRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 13:46
 * @describe 表详情信息
 */
@Service
public class TableRelationServiceImpl implements TableRelationService {

    @Autowired
    private TableRelationMapper mapper;

    @Override
    public List<TableRelation> getTableRelation(String tableName) {
        List<TableRelation> result = new ArrayList<>();
        //是否是源表
        String databaseName = tableName.toLowerCase().split("\\.")[0];
        boolean isOrigin = "dwd".contains(databaseName) || "dws".contains(databaseName) || "dma".contains(databaseName);
        //源数据
        if (!isOrigin) {
            result.add(mapper.getTableRelationOriginName(tableName));
            return result;
        }
        //子结点
        return buildTableTree(tableName);
    }

    /**
     * 获取表对应的所有子结点
     *
     * @param tableName
     * @return
     */
    private List<TableRelation> buildTableTree(String tableName) {
        List<TableRelation> allTables = new ArrayList<>();
        TableRelation relation = mapper.getTableRelationByName(tableName);
        if (relation == null){
            return allTables;
        }
        List<TableRelation> tableRelations = mapper.getTableRelationById(relation.getTableId());
        relation.setChildren(tableRelations);
        allTables.add(relation);
        //非源表表名
        for (TableRelation tableRelation : tableRelations) {
            String databaseName = tableRelation.getName().toLowerCase().split("\\.")[0];
            boolean isOrigin = "dwd".contains(databaseName) || "dws".contains(databaseName) || "dma".contains(databaseName);
            if (isOrigin) {
                TableRelation childTable = mapper.getTableRelationByName(tableRelation.getName());
                if (childTable == null) {
                    continue;
                }
                List<TableRelation> childTableRelations = mapper.getTableRelationById(childTable.getTableId());
                childTable.setChildren(childTableRelations);
                allTables.add(childTable);
            }
        }
        return packageTable(allTables,tableName);
    }

    /**
     * 拼接表的树形结构
     * @param tableRelationList
     * @param tableName
     * @return
     */
    private List<TableRelation> packageTable(List<TableRelation> tableRelationList, String tableName) {
        List<TableRelation> resultTables = new ArrayList<>();
        for (int index = 1; index < tableRelationList.size(); index++) {
            //第一个为根结点
            List<TableRelation> rootChildList = tableRelationList.get(0).getChildren();
            for (TableRelation childTable:rootChildList){
                if (childTable.getName().equals(tableRelationList.get(index).getName())){
                    childTable.setChildren(tableRelationList.get(index).getChildren());
                }
            }
        }
        resultTables.add(tableRelationList.get(0));
        return resultTables;
    }

}
