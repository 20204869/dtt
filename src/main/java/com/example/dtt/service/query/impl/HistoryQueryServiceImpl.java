package com.example.dtt.service.query.impl;

import com.example.dtt.domain.entity.query.HistoryQuery;
import com.example.dtt.mapper.query.HistoryQueryMapper;
import com.example.dtt.service.query.HistoryQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 13:46
 * @describe 数据查询 历史查询记录 业务层
 */
@Service
public class HistoryQueryServiceImpl implements HistoryQueryService {

    @Autowired
    private HistoryQueryMapper queryMapper;

    @Override
    public List<HistoryQuery> historyQueryByUserName(String userName) {
        return queryMapper.historyQueryByUserName(userName);
    }

    @Override
    public int saveQuery(HistoryQuery historyQuery) {
        return queryMapper.saveQuery(historyQuery);
    }
}
