package com.example.dtt.service.map.impl;

import com.example.dtt.domain.TreeSelect;
import com.example.dtt.domain.entity.map.Table;
import com.example.dtt.mapper.map.TableMapper;
import com.example.dtt.service.map.TableService;
import com.example.dtt.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author reid
 * @date 2021/11/23 13:46
 * @describe 数据库信息获取 业务层
 */
@Service
public class TableServiceImpl implements TableService {

    @Autowired
    private TableMapper tableMapper;

    @Override
    public List<Table> selectTableList(Table table) {
        List<Table> colsTableList = tableMapper.selectTableList(table);

        return ListUtils.removeDuplicateWithOrder(colsTableList);
    }

    @Override
    public List<Table> selectTableByTableName(String tableName) {
        return tableMapper.selectTableByTableName(tableName);
    }

    @Override
    public List<TreeSelect> dbTableList() {
        List<TreeSelect> dbs = new ArrayList<>();
        List<TreeSelect> chileTables = new ArrayList<>();
        List<Table> dbTableList = tableMapper.dbTableList();
        Map<String,List<Table>> dbTableMap=dbTableList.stream().collect(Collectors.groupingBy(Table::getDbName));

        Iterator<Map.Entry<String,List<Table>>> iterable=dbTableMap.entrySet().iterator();
        while(iterable.hasNext()){
            TreeSelect db = new TreeSelect();
            Long dbId = 0L;
            Map.Entry<String,List<Table>>entry=iterable.next();
            //遍历同一库下的所有表
            for (Table table:entry.getValue()){
                TreeSelect childTable = new TreeSelect();
                childTable.setId(table.getTableId());
                childTable.setLabel(table.getTableName());
                chileTables.add(childTable);
                dbId = table.getDbId();
            }
            //设置父节点及子节点【table列表】
            db.setId(dbId);
            db.setLabel(entry.getKey());
            db.setChildren(chileTables);
            dbs.add(db);
        }
        return dbs;
    }

    @Override
    public Table selectByTableId(Long tableId) {
        return tableMapper.selectByTableId(tableId);
    }
}
