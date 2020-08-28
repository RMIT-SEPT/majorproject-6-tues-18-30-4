package com.yuesun.base.security.role.service;

public interface RoleMenuService {
    Long[] findMenuIdsByRoleIds(Long[] roleIds);
    void deleteByRoleId(Long roleId);
    public void saveRoleMenus(Long roleId, Long[] menuIds);
}
