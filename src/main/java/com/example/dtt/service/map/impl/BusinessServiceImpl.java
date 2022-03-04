package com.example.dtt.service.map.impl;

import com.example.dtt.domain.entity.map.Business;
import com.example.dtt.mapper.map.BusinessMapper;
import com.example.dtt.service.map.BusinessService;
import com.example.dtt.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 13:46
 * @describe 业务表信息处理
 */
@Service
public class BusinessServiceImpl implements BusinessService {

    @Autowired
    private BusinessMapper mapper;


    @Override
    public List<Business> buinessList(String searchValue) {
        List<Business> allTables = mapper.buinessList(searchValue);
        return ListUtils.removeDuplicateWithOrder(allTables);
    }

    @Override
    public List<Business>  colsFromTableName(String tableName) {
        return mapper.colsFromTableName(tableName);
    }
}
