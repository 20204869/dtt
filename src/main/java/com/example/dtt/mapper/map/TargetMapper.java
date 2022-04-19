package com.example.dtt.mapper.map;


import com.example.dtt.domain.entity.map.Target;
import org.apache.hadoop.yarn.webapp.hamlet.Hamlet;

import java.util.List;

/**
 * @author reid
 * @date 2021/11/23 13:48
 * @describe 指标维度 数据库层
 */
public interface TargetMapper {

    /**
     * 通过指标名查询指标
     * @param targetName
     * @return
     */
    Target targetByName(String targetName);
    /**
     * 分页查询指标
     * @param target
     * @return
     */
    List<Target> selectTargetList(Target target);

    /**
     * 新增指标
     * @param target
     * @return
     */
    int saveTarget(Target target);

    /**
     * 更新指标
     * @param target
     * @return
     */
    int updateTarget(Target target);

    /**
     * 删除指标
     * @param targetId
     * @return
     */
    int deleteTarget(Long[] targetId);

    /**
     * 指标名称不可重复
     * @param targetName
     * @return
     */
    int checkTargetName(String targetName);

    /**
     * 根据指标编号获取指标详情
     * @param targetId
     * @return
     */
    Target getTargetDetailById(Long targetId);

    /**
     * 根据结果表查询指标信息
     * @param resultTable
     * @return
     */
    Target getTargetDetailByTable(String resultTable);
}
