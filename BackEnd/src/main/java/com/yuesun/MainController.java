package com.yuesun;

import com.yuesun.base.security.loginvo.LoginInfo;
import com.yuesun.base.security.menu.entity.Menu;
import com.yuesun.base.security.menu.service.MenuService;
import com.yuesun.base.security.role.entity.Role;
import com.yuesun.base.security.role.service.RoleService;
import com.yuesun.base.security.role.service.UserRoleService;
import com.yuesun.base.security.shortcut.service.ShortCutService;
import com.yuesun.base.security.user.entity.User;
import com.yuesun.base.security.user.service.UserService;
import com.yuesun.core.constant.HttpResultCode;
import com.yuesun.core.controller.AdminController;
import com.yuesun.core.entity.HttpResult;
import com.yuesun.core.util.HttpResultUtil;
import com.yuesun.core.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

@Controller
public class MainController extends AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private ShortCutService shortCutService;
    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("login")
    @ResponseBody
    public HttpResult login(
            HttpServletRequest request,
            @RequestParam(value = "loginName", required = true) String loginName,
            @RequestParam(value = "password", required = true) String password) {

        User user = userService.findByLoginNameAndPasswrod(loginName, password);
        if (user != null) {
            request.getSession().setAttribute(SESSION_CURRENT_USER, user);
            setCurrentRoles();
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setMenuVoList(loginInfo.getMenuVoList1(mMenuData()));
            loginInfo.setUser(user);
            return HttpResultUtil.createHttpResultSuccess(user.getLoginName() + "success！",loginInfo);
        } else {
            return HttpResultUtil.createHttpResult(HttpResultCode.UNKNOW_LOGINNAME_PASSWORD,"UNKNOW_LOGINNAME_PASSWORD");
        }
    }

    @PostMapping("toRegister")
    @ResponseBody
    public HttpResult login(User user) {
        User byLoginName = userService.findByLoginName(user.getLoginName());
        if (byLoginName != null){
            return HttpResultUtil.createSystemError("user 【"+user.getLoginName()+"】 already exist");
        }
        User save = userService.save(user);
        Role customer = roleService.findByRoleName("Customer");
        userRoleService.saveUserRoles(save.getId(),new Long[]{customer.getId()});
        return HttpResultUtil.createHttpResultSuccess("sign in success");
    }

    @PostMapping("updatePassword")
    @ResponseBody
    public HttpResult mUpdatePassword(
            @RequestParam(value = "oldPassword", required = true) String oldPassword,
            @RequestParam(value = "newPassword", required = true) String newPassword) {
        if (!checkNewPassword(newPassword)) {
            return returnValidationError("password length is 6 - 20");
        }
        if (!checkOldPassword(oldPassword)) {
            return HttpResultUtil
                    .createHttpResult(HttpResultCode.INPUT_OLD_PASSWORD_ERROR);
        }
        userService.resetPassword(getCurrentUser().getId(), newPassword);
        return returnSuccess("success!");
    }


    private boolean checkNewPassword(String newPassword) {
        if (newPassword.length() > 20 || newPassword.length() < 6) {
            return false;
        }
        return true;
    }

    private boolean checkOldPassword(String oldPassword) {
        try {
            String password = getCurrentUser().getPassword();
            oldPassword = SecurityUtil.encodeMd5(oldPassword);
            if (oldPassword.equals(password)) {
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @PostMapping("menuData")
    @ResponseBody
    public List<Menu> mMenuData() {
        List<Role> roleList = getCurrentRoles();
        Long[] roleIds = EtlRoleList(roleList);
        List<Menu> menuList = menuService.findByRoleIds(roleIds);
        return menuList;
    }

    private Long[] EtlRoleList(List<Role> roleList) {
        Long[] roleIds = new Long[roleList.size()];
        int i = 0;
        for (Role role : roleList) {
            roleIds[i] = role.getId();
            i++;
        }
        return roleIds;
    }

    private void setCurrentRoles() {
        User user = getCurrentUser();
        List<Role> roleList = roleService.findByUserId(user.getId());
        if (roleList.isEmpty()) {
            currentUserIsRoleIsNullHandler();
        }
        super.setCurrentRoles(roleList);
    }

    private void currentUserIsRoleIsNullHandler() {
        throw new NullPointerException("no role");
    }

    @PostMapping("shortcut")
    @ResponseBody
    public Object mShortcut() {
        Long userId = getCurrentUser().getId();
        List<Menu> allMenu = getCurrentMenus();
        return shortCutService.findShortCutMenuVo(userId, allMenu);
    }

    @PostMapping("saveShortcut")
    @ResponseBody
    public HttpResult mSaveShortcut(String menuIds) {
        if (StringUtils.isBlank(menuIds)) {
            return returnValidationError("menu is null");
        }
        String[] menuIdsStr = menuIds.split(",");
        Long[] longMenuIds = Arrays.asList(menuIdsStr).stream().toArray(Long[]::new);
        shortCutService.save(getCurrentUser().getId(), longMenuIds);
        return returnSuccess("success");
    }




}
