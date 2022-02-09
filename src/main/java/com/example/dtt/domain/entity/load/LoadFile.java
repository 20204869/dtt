package com.example.dtt.domain.entity.load;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author reid
 * @date 2021/12/24 14:54
 * @describe 上传文件 对象
 */
public class LoadFile implements Serializable {
    private static final long serialVersionUID = 1L;
    /** 搜索值 */
    private String searchValue;
    /** 请求参数 */
    private Map<String, Object> params;
    /**
     * 文件id
     */
    private Long fileId;
    /**
     * 配置id
     */
    private Long confId;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件名称
     */
    private String filePath;

    /**
     * 上传用户
     */
    private String loadUser;

    /**
     * 是否存在 0 存在 1 删除
     */
    private String delFlag;

    /** 加载时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date loadTime;

    private LoadFileConf fileConf;

    public LoadFileConf getFileConf() {
        return fileConf;
    }

    public void setFileConf(LoadFileConf fileConf) {
        this.fileConf = fileConf;
    }

    public Long getConfId() {
        return confId;
    }

    public void setConfId(Long confId) {
        this.confId = confId;
    }

    public String getSearchValue()
    {
        return searchValue;
    }

    public void setSearchValue(String searchValue)
    {
        this.searchValue = searchValue;
    }

    public Map<String, Object> getParams()
    {
        if (params == null)
        {
            params = new HashMap<>();
        }
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getLoadUser() {
        return loadUser;
    }

    public void setLoadUser(String loadUser) {
        this.loadUser = loadUser;
    }


    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Date getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(Date loadTime) {
        this.loadTime = loadTime;
    }

    @Override
    public String toString() {
        return "LoadFile{" +
                "fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", loadUser='" + loadUser + '\'' +
                ", delFlag='" + delFlag + '\'' +
                ", loadTime=" + loadTime +
                '}';
    }
}
