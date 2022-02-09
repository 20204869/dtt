package com.example.dtt.domain.entity.load;


import com.example.dtt.domain.BaseEntity;

/**
 * @author reid
 * @date 2021/12/24 14:54
 * @describe 上传文件配置管理 对象
 */
public class LoadFileConf extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long confId;
    /**
     * 业务系统
     */
    private String business;
    /**
     * 业务类型
     */
    private String fileType;

    /**
     * hive 表名
     */
    private String hiveTable;
    /**
     *  Hive 表前缀
     */
    private String tablePrefix;

    /**
     * hdfs 路径
     */
    private String hdfsPath;

    /**
     * 是否存在 0 存在 1 删除
     */
    private String delFlag;

    public Long getConfId() {
        return confId;
    }

    public void setConfId(Long confId) {
        this.confId = confId;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getHiveTable() {
        return hiveTable;
    }

    public void setHiveTable(String hiveTable) {
        this.hiveTable = hiveTable;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public void setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
    }

    public String getHdfsPath() {
        return hdfsPath;
    }

    public void setHdfsPath(String hdfsPath) {
        this.hdfsPath = hdfsPath;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "LoadConf{" +
                "confId=" + confId +
                ", business='" + business + '\'' +
                ", fileType='" + fileType + '\'' +
                ", hiveTable='" + hiveTable + '\'' +
                ", tablePrefix='" + tablePrefix + '\'' +
                ", hdfsPath='" + hdfsPath + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }
}
