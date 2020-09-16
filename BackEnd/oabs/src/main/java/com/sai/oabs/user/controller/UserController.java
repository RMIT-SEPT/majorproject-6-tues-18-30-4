package com.sai.oabs.user.controller;

import com.sai.oabs.user.entity.User;
import com.sai.oabs.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("login")
    public Map<String,Object> login(String username, String password){
        Map<String ,Object> result = new HashMap<>();
        User user = userService.findByUserNameAndPwd(username, password);
        if (user != null){
            result.put("code","1");
            result.put("msg","success");
        }else{
            result.put("code","0");
            result.put("msg","please input right username and password");
        }
        return result;
    }

}
