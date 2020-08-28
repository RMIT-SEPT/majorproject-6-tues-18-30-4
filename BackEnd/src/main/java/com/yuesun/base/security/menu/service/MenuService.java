package com.yuesun.base.security.menu.service;
import com.yuesun.base.security.loginvo.LoginInfo;
import com.yuesun.base.security.menu.entity.Menu;
import com.yuesun.core.vo.RoleMenuListVo;
import org.springframework.data.domain.Page;

import java.util.List;


public interface MenuService {

    Menu save(Menu menu);
    LoginInfo findAllMenu();
    List<Menu> findAll();
    Page<Menu> findByPage(Integer pageNumber, Integer size);
    List<Menu> findByRoleIds(Long[] roleIds);
    void delete(Long id);
    List<RoleMenuListVo> menuData(List<Menu> menuList);
}
