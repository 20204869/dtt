package com.example.dtt.domain.entity.query;

import com.example.dtt.domain.BaseEntity;

/**
 * @author reid
 * @date 2021/11/25 10:31
 * @describe 数据查询 历史查询记录
 */
public class HistoryQuery extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long id;
    /**
     * 查询执行者
     */
    private String executor;
    /**
     * 查询执行的SQL
     */
    private String querySql;
    /**
     * 多久前执行
     */
    private String times;

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getQuerySql() {
        return querySql;
    }

    public void setQuerySql(String querySql) {
        this.querySql = querySql;
    }

    @Override
    public String toString() {
        return "HistoryQuery{" +
                "id=" + id +
                ", executor='" + executor + '\'' +
                ", querySql='" + querySql + '\'' +
                '}';
    }
}
