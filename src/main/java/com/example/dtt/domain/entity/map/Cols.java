package com.example.dtt.domain.entity.map;

import com.example.dtt.domain.BaseEntity;

/**
 * @author reid
 * @date 2021/11/25 10:31
 * @describe 表字段信息
 */
public class Cols extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long tblId;
    private String colName;
    private String colType;
    private String comment;

    public Cols() {
    }

    public Long getTblId() {
        return tblId;
    }

    public void setTblId(Long tblId) {
        this.tblId = tblId;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getColType() {
        return colType;
    }

    public void setColType(String colType) {
        this.colType = colType;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
