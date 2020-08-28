package com.yuesun.core.vo;

import java.util.ArrayList;
import java.util.List;

public class RoleMenuListVo {
    private String id;
    private String text;
    private String state = "open";
    private String iconCls;
    private boolean checked = false;
    private String url;
    private List<RoleMenuListVo> children = new ArrayList<RoleMenuListVo>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getIconCls() {
        return iconCls;
    }

    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<RoleMenuListVo> getChildren() {
        return children;
    }

    public void setChildren(List<RoleMenuListVo> children) {
        this.children = children;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
