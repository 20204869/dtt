package com.example.dtt.service.map;

import com.example.dtt.domain.entity.map.Business;
import com.example.dtt.domain.entity.map.TableRelation;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 14:20
 * @describe
 */
public interface BusinessService {
    /**
     * 根据条件查询表
     * @param searchValue
     * @return
     */
    List<Business> buinessList(String searchValue);


    /**
     * 通过表名及数据库名获取业务表字段信息
     * @param tableName
     * @return
     */
    List<Business> colsFromTableName(String tableName);
}
