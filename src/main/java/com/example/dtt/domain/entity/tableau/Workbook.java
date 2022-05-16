package com.example.dtt.domain.entity.tableau;

import com.example.dtt.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author reid
 * @date 2022/5/5 13:28
 * @describe
 */
public class Workbook extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long workbookId;
    private String workbookName;
    private String workbookUrl;
    private Long projectId;
    private String projectName;
    /** 用户是否存在此tableau标识 默认不存在 */
    private boolean flag = false;

    /** 用户组 */
    private Long[] userIds;

    public Workbook() {
    }

    public Long[] getUserIds() {
        return userIds;
    }

    public void setUserIds(Long[] userIds) {
        this.userIds = userIds;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public Long getWorkbookId() {
        return workbookId;
    }

    public void setWorkbookId(Long workbookId) {
        this.workbookId = workbookId;
    }

    public String getWorkbookName() {
        return workbookName;
    }

    public void setWorkbookName(String workbookName) {
        this.workbookName = workbookName;
    }

    public String getWorkbookUrl() {
        return workbookUrl;
    }

    public void setWorkbookUrl(String workbookUrl) {
        this.workbookUrl = workbookUrl;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("workbookId", getWorkbookId())
                .append("workbookName", getWorkbookName())
                .append("workbookUrl", getWorkbookUrl())
                .append("projectId", getProjectId())
                .append("projectName", getProjectName())
                .toString();
    }
}
