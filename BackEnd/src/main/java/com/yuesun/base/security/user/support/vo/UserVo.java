package com.yuesun.base.security.user.support.vo;

import com.yuesun.base.security.user.entity.User;

public class UserVo extends User {
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
