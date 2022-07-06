package com.example.dtt.domain.entity.query;

import com.example.dtt.domain.BaseEntity;

/**
 * @author reid
 * @date 2021/11/25 10:31
 * @describe 执行日志
 */
public class QueryLog extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * yarn applicationID
     */
    private String yarnId;
    /**
     * 用户账号
     */
    private String userName;
    /**
     * 日志
     */
    private String sqlLog;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYarnId() {
        return yarnId;
    }

    public void setYarnId(String yarnId) {
        this.yarnId = yarnId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSqlLog() {
        return sqlLog;
    }

    public void setSqlLog(String sqlLog) {
        this.sqlLog = sqlLog;
    }
}
