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
    public List<Table> tableListBydbId(Long dbId) {
        return tableMapper.tableListBydbId(dbId);
    }

    @Override
    public Table selectByTableId(Long tableId) {
        return tableMapper.selectByTableId(tableId);
    }

    @Override
    public Table selectByTableName(String tableName) {
        return tableMapper.selectByTableName(tableName);
    }
}
