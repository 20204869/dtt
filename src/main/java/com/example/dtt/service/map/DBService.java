package com.example.dtt.service.map;


import com.example.dtt.domain.entity.map.DB;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 13:34
 * @describe 数据库业务层
 */
public interface DBService {
    /**
     * 根据条件查询数据库信息
     * @param db
     * @return
     */
    List<DB> selectDBList(DB db);
}
