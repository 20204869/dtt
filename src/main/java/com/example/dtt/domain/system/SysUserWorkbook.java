package com.example.dtt.domain.system;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 用户和tableau workbook 关联
 */
public class SysUserWorkbook {
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * workbookID
     */
    private Long workbookId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getWorkbookId() {
        return workbookId;
    }

    public void setWorkbookId(Long workbookId) {
        this.workbookId = workbookId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("workbookId", getWorkbookId())
                .toString();
    }
}
