package com.example.dtt.service.map;


import com.example.dtt.domain.entity.map.Cols;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 13:34
 * @describe 字段业务层
 */
public interface ColService {
    /**
     * 根据表查询字段信息
     * @param tblId
     * @return
     */
    List<Cols> selectColByTblId(Long tblId);

    /**
     * 根据表查询字段信息
     * @param tblId
     * @return
     */
    List<Cols> ColsByTblId(Long tblId);

    List<Cols> selectColByTblName(String tblName);
}
