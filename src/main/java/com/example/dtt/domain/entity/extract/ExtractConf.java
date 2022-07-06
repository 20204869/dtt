package com.example.dtt.domain.entity.extract;

import com.example.dtt.annotation.Excel;
import com.example.dtt.domain.BaseEntity;

/**
 * @author reid
 * @date 2022/3/27 16:02
 * @describe 取数模板 对象
 */
public class ExtractConf extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 模板名
     */
    private String templateName;
    /**
     * 取数模板数据对应的SQL
     */
    private String templateSql;
    /**
     * 提出需求者
     */
    private String demandSide;

    /**
     * 数据处理负责人
     */
    private String handler;

    /** 用户是否存在此角色标识 默认不存在 */
    private boolean flag = false;

    /**
     * 是否删除 0 存在 1 删除
     */
    private String delFlag;

    /** 用户组 */
    private Long[] userIds;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Long[] getUserIds() {
        return userIds;
    }

    public void setUserIds(Long[] userIds) {
        this.userIds = userIds;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateSql() {
        return templateSql;
    }

    public void setTemplateSql(String templateSql) {
        this.templateSql = templateSql;
    }

    public String getDemandSide() {
        return demandSide;
    }

    public void setDemandSide(String demandSide) {
        this.demandSide = demandSide;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "ExtractConf{" +
                "id=" + id +
                ", templateName='" + templateName + '\'' +
                ", templateSql='" + templateSql + '\'' +
                ", demandSide='" + demandSide + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
