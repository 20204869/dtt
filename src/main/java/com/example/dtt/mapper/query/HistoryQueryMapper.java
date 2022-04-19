package com.example.dtt.mapper.query;


import com.example.dtt.domain.entity.query.HistoryQuery;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 13:48
 * @describe 历史查询记录  数据层
 */
public interface HistoryQueryMapper {
    /**
     * 根据用户查询改用户历史执行过的SQL
     *
     * @param userName
     * @return
     */
    List<HistoryQuery> historyQueryByUserName(String userName);

    /**
     * 保存查询记录
     *
     * @param historyQuery
     * @return
     */
    int saveQuery(HistoryQuery historyQuery);

}
