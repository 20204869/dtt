package com.example.dtt.mapper.map;

import com.example.dtt.domain.entity.map.DB;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 13:48
 * @describe 数据库信息  数据层
 */
public interface DBMapper {
    /**
     * 根据条件查询数据库信息
     * @param db
     * @return
     */
    List<DB> selectDBList(DB db);
}
