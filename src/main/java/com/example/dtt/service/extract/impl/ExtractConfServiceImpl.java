package com.example.dtt.service.extract.impl;

import com.example.dtt.domain.entity.extract.ExtractConf;
import com.example.dtt.domain.entity.system.SysRole;
import com.example.dtt.domain.system.SysUserRole;
import com.example.dtt.domain.system.SysUserTemplate;
import com.example.dtt.mapper.extract.ExtractConfMapper;
import com.example.dtt.mapper.extract.UserTemplateMapper;
import com.example.dtt.service.extract.ExtractConfService;
import com.example.dtt.utils.SpringUtils;
import com.example.dtt.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author reid
 * @date 2022/3/27 16:33
 * @describe
 */
@Service
public class ExtractConfServiceImpl implements ExtractConfService {

    @Autowired
    private ExtractConfMapper confMapper;

    @Autowired
    private UserTemplateMapper templateMapper;

    @Override
    public List<ExtractConf> confListByUserId(String templateName,Long userId) {
        return confMapper.confListByUserId(templateName,userId);
    }
    @Override
    public List<Long> selectUserListById(Long id) {
        return confMapper.selectUserListById(id);
    }

    @Override
    public List<ExtractConf> confListPaging(ExtractConf conf) {
        return confMapper.confListPaging(conf);
    }

    @Override
    public List<ExtractConf> selectExtractConfAll() {
        return SpringUtils.getAopProxy(this).confListPaging(new ExtractConf());
    }

    /**
     * 根据用户ID查询取数模板信息
     *
     * @param userId 用户ID
     * @return 角色列表
     */
    @Override
    public List<ExtractConf> extractConfByUserId(Long userId) {
        List<ExtractConf> userExtractConfs = confMapper.selectExtractConfByUserId(userId);
        List<ExtractConf> templates = selectExtractConfAll();
        for (ExtractConf extractConf : templates) {
            for (ExtractConf userConf : userExtractConfs) {
                if (extractConf.getId().longValue() == userConf.getId().longValue()) {
                    extractConf.setFlag(true);
                    break;
                }
            }
        }
        return templates;
    }

    @Override
    public int saveConf(ExtractConf conf) {
        int rows = confMapper.saveConf(conf);
        insertExtractUser(conf);
        return rows;

    }

    public void insertExtractUser(ExtractConf conf) {
        Long[] users = conf.getUserIds();
        if (StringUtils.isNotNull(users)) {
            // 新增模板与用户管理
            List<SysUserTemplate> list = new ArrayList<>();
            for (Long userId : users) {
                SysUserTemplate ut = new SysUserTemplate();
                ut.setUserId(userId);
                ut.setTemplateId(conf.getId());
                list.add(ut);
            }
            if (list.size() > 0) {
                templateMapper.batchUserTemplate(list);
            }
        }
    }

    @Override
    public int updateConf(ExtractConf conf) {
        Long templateId = conf.getId();
        // 删除用户与模板的关联
        templateMapper.deleteUserTemplateById(templateId);
        // 新增用户与角色管理
        insertExtractUser(conf);
        return confMapper.updateConf(conf);
    }

    @Override
    public ExtractConf queryConfById(Long id) {
        return confMapper.queryConfById(id);
    }

    @Override
    @Transactional
    public void insertUserTemplate(Long id, Long[] userIds) {
        templateMapper.deleteUserTemplateById(id);
        insertUserTemplates(id, userIds);
    }

    /**
     * 新增取数模板用户信息
     */
    public void insertUserTemplates(Long id, Long[] userIds) {
        if (StringUtils.isNotNull(userIds)) {
            // 新增用户与取数模板
            List<SysUserTemplate> list = new ArrayList<>();
            for (Long userId : userIds) {
                SysUserTemplate ur = new SysUserTemplate();
                ur.setUserId(userId);
                ur.setTemplateId(id);
                list.add(ur);
            }
            if (list.size() > 0) {
                templateMapper.batchUserTemplate(list);
            }
        }
    }

    @Override
    public ExtractConf queryConfByName(String name) {
        return confMapper.queryConfByName(name);
    }

    @Override
    public int deleteConfById(Long id) {
        templateMapper.deleteUserTemplateById(id);
        return confMapper.deleteConfById(id);
    }

    @Override
    public int deleteConfByIds(Long[] ids) {
        templateMapper.deleteUserTemplateByIds(ids);
        return confMapper.deleteConfByIds(ids);
    }
}
