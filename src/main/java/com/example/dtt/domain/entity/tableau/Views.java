package com.example.dtt.domain.entity.tableau;

import com.example.dtt.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author reid
 * @date 2022/5/5 13:30
 * @describe
 */
public class Views extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long viewId;
    private String viewName;
    private String viewUrl;

    public Long getViewId() {
        return viewId;
    }

    public void setViewId(Long viewId) {
        this.viewId = viewId;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getViewUrl() {
        return viewUrl;
    }

    public void setViewUrl(String viewUrl) {
        this.viewUrl = viewUrl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("viewId", getViewId())
                .append("viewName", getViewName())
                .append("viewUrl", getViewUrl())
                .toString();
    }
}
