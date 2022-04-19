package com.example.dtt.service.map.impl;

import com.example.dtt.domain.entity.map.Target;
import com.example.dtt.domain.entity.system.SysUser;
import com.example.dtt.exception.ServiceException;
import com.example.dtt.mapper.map.TargetMapper;
import com.example.dtt.service.map.TargetService;
import com.example.dtt.utils.SecurityUtils;
import com.example.dtt.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(TargetServiceImpl.class);

    @Autowired
    private TargetMapper targetMapper;

    @Override
    public String importTarget(List<Target> targetList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(targetList) || targetList.size() == 0) {
            throw new ServiceException("导入指标数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (Target target : targetList) {
            try {
                // 验证是否存在这个用户
                Target t = targetMapper.targetByName(target.getTargetName());
                if (StringUtils.isNull(t)) {
                    target.setCreateBy(operName);
                    this.saveTarget(target);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、指标 " + target.getTargetName() + " 导入成功");
                } else if (isUpdateSupport) {
                    target.setUpdateBy(operName);
                    this.updateTarget(target);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、指标 " + target.getTargetName() + " 更新成功");
                } else {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、指标 " + target.getTargetName() + " 已存在");
                }
            } catch (Exception e) {
                failureNum++;
                String msg = "<br/>" + failureNum + "、指标 " + target.getTargetName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

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
