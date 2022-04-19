package com.example.dtt.domain.entity.map;


import com.example.dtt.annotation.Excel;
import com.example.dtt.domain.BaseEntity;

/**
 * @author reid
 * @date 2021/11/22 16:48
 * @describe 指标维度对象
 */
public class Target extends BaseEntity {
    private static final long serialVersionUID = 1L;
    /**
     * 指标id
     */
    @Excel(name="指标编号",cellType = Excel.ColumnType.NUMERIC,prompt = "指标编号")
    private Long targetId;
    /**
     * 指标分类
     */
    @Excel(name="指标分类")
    private String demandSide;
    /**
     * 指标名称
     */
    @Excel(name = "指标名称")
    private String targetName;
    /**
     * 指标定义
     */
    @Excel(name = "指标定义")
    private String targetCal;
    /**
     * 业务口径
     */
    @Excel(name = "业务口径")
    private String demandCal;
    /**
     * 口径处理的数据来源系统
     */
    @Excel(name = "数据来源")
    private String handerSys;
    /**
     * IT数据负责人
     */
    @Excel(name = "IT负责人")
    private String handerUser;
    /**
     * 需求人
     */
    @Excel(name = "需求人")
    private String demandUser;
    /**
     * 结果表
     */
    @Excel(name = "结果表")
    private String resultTable;

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getDemandSide() {
        return demandSide;
    }

    public void setDemandSide(String demandSide) {
        this.demandSide = demandSide;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetCal() {
        return targetCal;
    }

    public void setTargetCal(String targetCal) {
        this.targetCal = targetCal;
    }

    public String getDemandCal() {
        return demandCal;
    }

    public void setDemandCal(String demandCal) {
        this.demandCal = demandCal;
    }

    public String getHanderSys() {
        return handerSys;
    }

    public void setHanderSys(String handerSys) {
        this.handerSys = handerSys;
    }

    public String getHanderUser() {
        return handerUser;
    }

    public void setHanderUser(String handerUser) {
        this.handerUser = handerUser;
    }

    public String getDemandUser() {
        return demandUser;
    }

    public void setDemandUser(String demandUser) {
        this.demandUser = demandUser;
    }

    public String getResultTable() {
        return resultTable;
    }

    public void setResultTable(String resultTable) {
        this.resultTable = resultTable;
    }

    @Override
    public String toString() {
        return "Target{" +
                "targetId=" + targetId +
                ", demandSide='" + demandSide + '\'' +
                ", targetName='" + targetName + '\'' +
                ", targetCal='" + targetCal + '\'' +
                ", demandCal='" + demandCal + '\'' +
                ", handerSys='" + handerSys + '\'' +
                ", handerUser='" + handerUser + '\'' +
                ", demandUser='" + demandUser + '\'' +
                ", resultTable='" + resultTable + '\'' +
                '}';
    }
}
