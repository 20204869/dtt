package com.example.dtt.service.query;


import com.example.dtt.domain.entity.query.HistoryQuery;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 13:34
 * @describe 数据查询 历史查询记录服务层
 */
public interface HistoryQueryService {
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
