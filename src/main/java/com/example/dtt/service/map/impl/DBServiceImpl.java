package com.example.dtt.service.map.impl;

import com.example.dtt.domain.entity.map.DB;
import com.example.dtt.mapper.map.DBMapper;
import com.example.dtt.service.map.DBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 13:46
 * @describe 数据库信息获取 业务层
 */
@Service
public class DBServiceImpl implements DBService {

    @Autowired
    private DBMapper dbMapper;
    /**
     * 查询数据库信息
     * @param db
     * @return
     */
    @Override
    public List<DB> selectDBList(DB db) {
        return dbMapper.selectDBList(db);
    }
}
