package com.example.dtt.service.map.impl;

import com.example.dtt.domain.entity.map.TableRelation;
import com.example.dtt.mapper.map.TableRelationMapper;
import com.example.dtt.service.map.TableRelationService;
import com.example.dtt.utils.StringUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
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
        boolean isOrigin = "dwd".contains(databaseName) || "dws".contains(databaseName) || "dma".contains(databaseName) || "dim".contains(databaseName) || "risk".contains(databaseName);
        //源数据
        if (!isOrigin) {
            TableRelation first = mapper.getTableRelationByName(tableName);
            if (first == null){
                first = new TableRelation(tableName);
                first.setTableSql("业务系统源数据表");
            }
            result.add(first);
            return result;
        }
        //子结点
        return buildTableTree(tableName);
    }


    private List<TableRelation> buildTableTree(String tableName) {
        List<TableRelation> result = new ArrayList<>();
        TableRelation first = mapper.getTableRelationByName(tableName);
        if (first == null) {
            first = new TableRelation(tableName);
            first.setTableSql("已下线表或其他情况，请咨询大数据工作人员！");
            result.add(first);
            return result;
        }
        first.setChildren(buildChildTable(first));
        result.add(first);
        return result;
    }

    private List<TableRelation> buildChildTable(TableRelation tableRelation) {
        List<TableRelation> childList = mapper.getTableRelationById(tableRelation.getTableId());
        for (TableRelation firstChildTable : childList) {
            TableRelation tmp = mapper.getTableRelationByName(firstChildTable.getName());
            if (tmp == null){
                continue;
            }
            Long tableId = tmp.getTableId();
            if (tableId == null){
                continue;
            }
            firstChildTable.setTableId(tmp.getTableId());
            firstChildTable.setChildren(buildChildTable(firstChildTable));
        }
        return childList;
    }

    /**
     * 拼接表的树形结构
     *
     * @param tableRelationList
     * @return
     */
    private List<TableRelation> packageTable(List<TableRelation> tableRelationList) {
        List<TableRelation> resultTables = new ArrayList<>();
        for (int index = 1; index < tableRelationList.size(); index++) {
            //第一个为根结点
            List<TableRelation> rootChildList = tableRelationList.get(0).getChildren();
            for (TableRelation childTable : rootChildList) {
                if (childTable.getName().equals(tableRelationList.get(index).getName())) {
                    childTable.setChildren(tableRelationList.get(index).getChildren());
                }
            }
        }
        resultTables.add(tableRelationList.get(0));
        return resultTables;
    }

}
