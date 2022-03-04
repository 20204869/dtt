package com.example.dtt.service.map.impl;

import com.example.dtt.domain.entity.map.Cols;
import com.example.dtt.mapper.map.ColMapper;
import com.example.dtt.service.map.ColService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 13:46
 * @describe 表字段获取 业务层
 */
@Service
public class ColServiceImpl implements ColService {

    @Autowired
    private ColMapper colMapper;

    /**
     * 获取表字段信息
     * @param tblId
     * @return
     */
    @Override
    public List<Cols> selectColByTblId(Long tblId) {
        return colMapper.selectColByTblId(tblId);
    }
}
