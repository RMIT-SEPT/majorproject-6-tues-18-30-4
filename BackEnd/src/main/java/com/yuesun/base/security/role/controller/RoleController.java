package com.yuesun.base.security.role.controller;

import com.yuesun.base.security.loginvo.LoginInfo;
import com.yuesun.base.security.menu.entity.Menu;
import com.yuesun.base.security.menu.service.MenuService;
import com.yuesun.base.security.role.entity.Role;
import com.yuesun.base.security.role.service.RoleMenuService;
import com.yuesun.base.security.role.service.RoleService;
import com.yuesun.core.controller.AdminController;
import com.yuesun.core.entity.HttpResult;
import com.yuesun.core.vo.RoleMenuListVo;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController("roleController")
@RequestMapping("/role")
public class RoleController extends AdminController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleMenuService roleMenuService;

    @PostMapping("/list")
    public Object list(){
        return roleService.findAll();
    }

    @PostMapping("/menu/allList")
    public LoginInfo allList() {
        return menuService.findAllMenu();
    }

    @GetMapping("menuList/{roleId}")
    public Object menuList(@PathVariable long roleId){
        List<Menu> allMenu = menuService.findAll();
        List<Menu> roleMenuList = menuService.findByRoleIds(new Long[]{roleId});
        List<RoleMenuListVo> menuData = getMenuData(allMenu, roleMenuList);
        return menuData;
    }

    private List<RoleMenuListVo> getMenuData(List<Menu> allMenu, List<Menu> roleMenuList) {
        List<RoleMenuListVo> menuData = transferToRoleMenuListVo(allMenu);
        Long[] roleMenuIds = getRoleMenuIds(roleMenuList);
        setRoleMenuListVoCheck(menuData,roleMenuIds);
        return menuData;
    }

    private void setRoleMenuListVoCheck(List<RoleMenuListVo> menuData, Long[] roleMenuIds) {
        for(RoleMenuListVo treeItem:menuData){
            Long menuId = Long.valueOf(treeItem.getId());
            if(ArrayUtils.contains(roleMenuIds,menuId)){
                treeItem.setChecked(true);
            }
            List<RoleMenuListVo> children = treeItem.getChildren();
            if(children.size()>0){
                setRoleMenuListVoCheck(children,roleMenuIds);
            }
        }
    }

    private Long[] getRoleMenuIds(List<Menu> roleMenuList) {
        Long[] roleMenuIds = new Long[roleMenuList.size()];
        int i=0;
        for (Menu menu:roleMenuList){
            roleMenuIds[i] = menu.getId();
            i++;
        }
        return roleMenuIds;
    }

    private List<RoleMenuListVo> transferToRoleMenuListVo(List<Menu> allMenu) {
        return  menuService.menuData(allMenu);
    }

    @PostMapping("/save")
    public HttpResult save(@Valid Role role, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return returnValidationError(bindingResult.getFieldError().getDefaultMessage());
        }
        roleService.save(role);
        return returnSuccess("success");
    }

    @PostMapping("/delete/{id}")
    public HttpResult delete(@PathVariable(required = true) long id){
        roleService.delete(id);
        return returnSuccess("success");
    }

    @PostMapping("/saveMenu")
    public HttpResult saveMenu(@RequestParam(value = "roleId", required = true) Long roleId,
                               @RequestParam(value = "menuIdStr", required = true) String menuIdStr){
        final String SEPARATOR = ",";
        String[] menuIds = menuIdStr.split(SEPARATOR);
        Long[] longMenuIds = Arrays.asList(menuIds).stream().toArray(Long[]::new);

        roleMenuService.saveRoleMenus(roleId,longMenuIds);
        return returnSuccess("success");
    }
}
