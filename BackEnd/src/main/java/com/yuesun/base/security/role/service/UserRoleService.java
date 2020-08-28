package com.yuesun.base.security.role.service;

import com.yuesun.base.security.role.entity.UserRole;

import java.util.List;

public interface UserRoleService {
    List<UserRole> findByUserIds(Long[] userIds);
    List<UserRole> findByUserId(Long userId);
    boolean isUsed(Long roleId);
    void saveUserRoles(Long userId, Long[] roleIds);
}
