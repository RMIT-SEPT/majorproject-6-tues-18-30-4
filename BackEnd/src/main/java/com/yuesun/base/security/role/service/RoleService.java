package com.yuesun.base.security.role.service;

import com.yuesun.base.security.role.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService {
    List<Role> findByUserId(Long userId);
    Map<Long,String> findRoleNameByUserIds(Long[] userIds);
    List<Role> findAll();
    Role save(Role role);
    void delete(Long id);
    List<Long> findMenuIdByRoleId(Long id);
    Role findByRoleName(String roleName);
}
