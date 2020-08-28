package com.yuesun.base.security.loginvo;

import com.yuesun.base.security.menu.entity.Menu;
import com.yuesun.base.security.menu.entity.MenuVo;
import com.yuesun.base.security.user.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LoginInfo {

    private User user;
    private List<MenuVo> menuVoList;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<MenuVo> getMenuVoList() {
        return menuVoList;
    }

    public void setMenuVoList(List<MenuVo> menuVoList) {
        this.menuVoList = menuVoList;
    }

    public static List<MenuVo> getMenuVoList1(List<Menu> menuList) {
        List<MenuVo> menuVoList = new ArrayList<>();


        List<Menu> rootMenuList = menuList.stream()
                .filter(menu -> menu.getParentId() == null)
                .collect(Collectors.toList());

        menuVoList.addAll(transFromMenuList(rootMenuList));


        for (MenuVo menuVo : menuVoList) {
            List<Menu> subMenuList = menuList.stream()
                    .filter(menu -> menuVo.getId().equals(menu.getParentId()+""))
                    .collect(Collectors.toList());
            menuVo.setSubMenu(transFromMenuList(subMenuList));
        }
        return menuVoList;
    }


    /**
     * 将menuList转换成MenuVoList
     *
     * @param
     * @return
     * @remark
     */
    private static List<MenuVo> transFromMenuList(List<Menu> menuList) {
        List<MenuVo> menuVoList = new ArrayList<>();
        if (!menuList.isEmpty()) {
            for (Menu menu : menuList) {
                MenuVo menuVo = new MenuVo();
                menuVo.setId(String.valueOf(menu.getId()));
                menuVo.setIcon(menu.getIcon());
                menuVo.setLabel(menu.getName());
                menuVo.setPath(menu.getIndexUrl());
                menuVoList.add(menuVo);
            }
        }
        return menuVoList;
    }
}
