package com.example.dtt.mapper.map;


import com.example.dtt.domain.entity.map.Cols;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 13:48
 * @describe 数据表字段信息  数据层
 */
public interface ColMapper {
    /**
     * 根据表查询字段信息
     * @param tblId
     * @return
     */
    List<Cols> selectColByTblId(Long tblId);
}
