package com.yuesun.base.security.role.service.impl;

import com.yuesun.base.security.role.entity.RoleMenu;
import com.yuesun.base.security.role.repository.RoleMenuRepository;
import com.yuesun.base.security.role.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("roleMenuService")
public class RoleMenuServiceImpl implements RoleMenuService {

    @Autowired
    private RoleMenuRepository roleMenuRepository;

    @Transactional(readOnly = false)
    @Override
    public Long[] findMenuIdsByRoleIds(Long[] roleIds) {
        List<RoleMenu> roleMenuList = roleMenuRepository.findRoleMenusByRoleIdIn(roleIds);
        return roleMenuList.stream()
                .map(RoleMenu::getMenuId)
                .toArray(Long[]::new);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteByRoleId(Long roleId) {
        roleMenuRepository.deleteByRoleId(roleId);
    }

    @Transactional(readOnly = false)
    @Override
    public void saveRoleMenus(Long roleId, Long[] menuIds) {
        deleteByRoleId(roleId);
        List<RoleMenu> roleMenuList = createRoleMenuList(roleId, menuIds);
        roleMenuList.forEach(t ->
                roleMenuRepository.save(t)
        );
    }

    private List<RoleMenu> createRoleMenuList(Long roleId, Long[] menuIds) {
        List<RoleMenu> roleMenuList = new ArrayList<>();
        for (Long menuId : menuIds) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenuList.add(roleMenu);
        }
        return roleMenuList;
    }
}
