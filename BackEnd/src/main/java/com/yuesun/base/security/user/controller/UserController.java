package com.yuesun.base.security.user.controller;

import com.yuesun.base.security.role.service.RoleService;
import com.yuesun.base.security.role.service.UserRoleService;
import com.yuesun.base.security.user.entity.User;
import com.yuesun.base.security.user.service.UserService;
import com.yuesun.base.security.user.support.vo.UserVo;
import com.yuesun.core.controller.AdminController;
import com.yuesun.core.entity.HttpResult;
import com.yuesun.core.entity.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController extends AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping("/findAll")
    public Object findAll() {
        return userService.findAll();
    }
    @PostMapping("/save")
    public HttpResult addUser(@Valid User user, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return returnValidationError(bindingResult.getFieldError().getDefaultMessage());
        }
        if (user.getId() == null) {
            insert(user);
        } else {
            update(user);
        }
        return returnSuccess("save user[" + user.getLoginName() + "]success");
    }
    public void insert(User user){
        userService.save(user);
    }
    public void update(User user){
        User oldUser = userService.findById(user.getId());
        if (oldUser == null) {
            throw new NullPointerException("no found user");
        }
        oldUser.setLoginName(user.getLoginName());
        oldUser.setName(user.getName());
        oldUser.setPhone(user.getPhone());
        userService.update(oldUser);
    }
    @PostMapping("delete/{id}")
    public HttpResult delete(@PathVariable Long id) {
        userService.deleteById(id);
        return returnSuccess("delete user id is[" + id + "]success");
    }
    @PostMapping("restoreInitialPassword/{id}")
    public HttpResult restoreInitialPassword(@PathVariable Long id) {
        String password = "123456";
        userService.resetPassword(id, password);
        return returnSuccess("reset user password[" + id + "],success");
    }

    @GetMapping("roleList/{id}")
    public Object roleList(@PathVariable Long id) {
        return roleService.findByUserId(id);
    }

    @PostMapping("saveRoles")
    public HttpResult saveRoles(@RequestParam(value = "userId", required = true) Long userId,
                                @RequestParam(value = "roleIdStr", required = true) String roleIdStr) {
        final String SEPARATOR = ",";
        String[] roleIds = roleIdStr.split(SEPARATOR);
        Long[] longMenuIds = Arrays.asList(roleIds).stream().toArray(Long[]::new);

        userRoleService.saveUserRoles(userId, longMenuIds);
        return returnSuccess("set user[" + userId + "],role[" + Arrays.toString(roleIds) + "]success");
    }

    @RequestMapping("list")
    public Object list(ServletRequest request) {
        PageRequest pageRequest = buildDataPage(request);
        Page rows = getPage(userService.findByPage(pageRequest));
        List<UserVo> userVoList = transformUserListToUserVoList(rows.getRows());
        rows.setRows(userVoList);
        return rows;
    }

    private List transformUserListToUserVoList(List<User> userList) {
        if (userList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        // get userIds
        Long[] userIds = userList.stream().map(User::getId).toArray(Long[]::new);

        // get key:userId,value:roleNamesMap
        Map<Long, String> userIdRoleNamesMap = roleService.findRoleNameByUserIds(userIds);


        List<UserVo> userVoList = new ArrayList<>();
        for (User u : userList) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(u, userVo);

            userVo.setRoleName(userIdRoleNamesMap.get(userVo.getId()));
            userVoList.add(userVo);
        }
        return userVoList;
    }
}
