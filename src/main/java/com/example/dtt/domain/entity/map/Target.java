package com.example.dtt.domain.entity.map;


import com.example.dtt.domain.BaseEntity;

/**
 * @author reid
 * @date 2021/11/22 16:48
 * @describe 指标维度对象
 */
public class Target extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long targetId;
    private String targetName;
    private String targetCal;
    private String handerSys;
    private String handerUser;
    private String demandSide;
    private String demandUser;
    private String resultTable;

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
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

    public String getDemandSide() {
        return demandSide;
    }

    public void setDemandSide(String demandSide) {
        this.demandSide = demandSide;
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
                ", targetName='" + targetName + '\'' +
                ", targetCal='" + targetCal + '\'' +
                ", handerSys='" + handerSys + '\'' +
                ", handerUser='" + handerUser + '\'' +
                ", demandSide='" + demandSide + '\'' +
                ", demandUser='" + demandUser + '\'' +
                ", resultTable='" + resultTable + '\'' +
                '}';
    }
}
