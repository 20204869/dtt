package com.example.dtt.service.map.impl;

import com.example.dtt.domain.entity.map.Target;
import com.example.dtt.mapper.map.TargetMapper;
import com.example.dtt.service.map.TargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 13:46
 * @describe 指标 数据服务层
 */
@Service
public class TargetServiceImpl implements TargetService {


    @Autowired
    private TargetMapper targetMapper;

    @Override
    public Target getTargetDetailByTable(String resultTable) {
        return targetMapper.getTargetDetailByTable(resultTable);
    }

    @Override
    public Target getTargetDetailById(Long targetId) {
        return targetMapper.getTargetDetailById(targetId);
    }

    @Override
    public List<Target> selectTargetList(Target target) {
        return targetMapper.selectTargetList(target);
    }

    @Override
    public int saveTarget(Target target) {
        return targetMapper.saveTarget(target);
    }

    @Override
    public int updateTarget(Target target) {
        return targetMapper.updateTarget(target);
    }

    @Override
    public int deleteTarget(Long[] targetId) {
        return targetMapper.deleteTarget(targetId);
    }

    @Override
    public int checkTargetName(String targetName) {
        return targetMapper.checkTargetName(targetName);
    }
}
