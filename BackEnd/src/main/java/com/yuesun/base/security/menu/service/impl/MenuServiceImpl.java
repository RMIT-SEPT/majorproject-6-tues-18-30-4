package com.yuesun.base.security.menu.service.impl;

import com.yuesun.base.security.loginvo.LoginInfo;
import com.yuesun.base.security.menu.entity.Menu;
import com.yuesun.base.security.menu.repository.MenuRepository;
import com.yuesun.base.security.menu.service.MenuService;
import com.yuesun.base.security.role.service.RoleMenuService;
import com.yuesun.core.vo.RoleMenuListVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@Service("menuService")
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private RoleMenuService roleMenuService;

    @Transactional(readOnly = false)
    @Override
    public Menu save(Menu menu) {
        menuRepository.save(menu);
        return menu;
    }

    @Override
    public LoginInfo findAllMenu() {
        List<Menu> menuList = findAll();
        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setMenuVoList(loginInfo.getMenuVoList1(menuList));
        return loginInfo;
    }

    @Override
    public List<Menu> findAll() {
        List<Menu> menuList = menuRepository.findAll(Sort.by(Sort.Direction.ASC,"seq"));
        setParentName(menuList);
        return menuList;
    }

    @Override
    public Page<Menu> findByPage(Integer pageNumber, Integer size) {
        return menuRepository.findAll(PageRequest.of(pageNumber,size));
    }

    @Override
    public List<Menu> findByRoleIds(Long[] roleIds) {
        Long[] menuIds = roleMenuService.findMenuIdsByRoleIds(roleIds);
        if (menuIds.length <= 0){
            return Collections.emptyList();
        }
        return menuRepository.findMenusByIdIn(menuIds);
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(Long id) {
        menuRepository.deleteById(id);
    }

    @Override
    public List<RoleMenuListVo> menuData(List<Menu> menuList) {
        List<RoleMenuListVo> menuData = new ArrayList<>();
        CopyOnWriteArrayList<Menu> copyMenuList = new CopyOnWriteArrayList<>(menuList);
        for (Menu menu : menuList) {
            if (menu.getParentId() == null) {
                RoleMenuListVo roleMenuListVo = createRoleMenuListVo(menu);
                menuData.add(roleMenuListVo);
                setChildren(roleMenuListVo, copyMenuList, menu.getId());
            }
        }
        return menuData;
    }

    private void setChildren(RoleMenuListVo easyUiTreeVo, List<Menu> menuList, Long parentId) {
        List<Menu> childrenList = findMenuChilds(menuList, parentId);
        if (childrenList.size() > 0) {
            List<RoleMenuListVo> childrenMenuData = new ArrayList<>();
            for (Menu subMenu : childrenList) {
                RoleMenuListVo subRoleMenuListVo = createRoleMenuListVo(subMenu);
                childrenMenuData.add(subRoleMenuListVo);
                setChildren(subRoleMenuListVo, menuList, subMenu.getId());
            }
            easyUiTreeVo.setChildren(childrenMenuData);
        }
    }

    private List<Menu> findMenuChilds(List<Menu> menuList, Long parentId) {
        List<Menu> childsmenu = new ArrayList<>();
        for (Menu menu : menuList) {
            if (parentId.equals(menu.getParentId())) {
                childsmenu.add(menu);
            }
        }
        return childsmenu;
    }

    private RoleMenuListVo createRoleMenuListVo(Menu menu) {
        final String SEPARATOR = "";
        RoleMenuListVo item = new RoleMenuListVo();
        item.setId(menu.getId() + SEPARATOR);
        item.setText(menu.getName());
//        item.setUrl(menu.getIndexUrl());
        return item;
    }

    private void setParentName(List<Menu> menuList) {
        List<Menu> parentMenuList = findParentMenuList(menuList);
        for (Menu menu : menuList) {
            if (menu.getParentId() != null) {
                for (Menu parentMenu : parentMenuList) {
                    if (menu.getParentId().equals(parentMenu.getId())) {
                        menu.setParentName(parentMenu.getName());
                    }
                }
            }
        }
    }

    private List<Menu> findParentMenuList(List<Menu> menuList) {
        List<Menu> parentMenuList = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menu.getParentId() == null) {
                parentMenuList.add(menu);
            }
        }
        return parentMenuList;
    }

}
