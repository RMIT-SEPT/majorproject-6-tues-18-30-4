package com.sai.oabs;

import com.sai.oabs.user.entity.User;
import com.sai.oabs.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping
public class MainController {

    @Autowired
    private UserService userService;

    @PostMapping("login")
    @ResponseBody
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

    @PostMapping("register")
    @ResponseBody
    public Map<String,Object> register(User user){
        Map<String ,Object> result = new HashMap<>();
        try {
            userService.save(user);
            result.put("code","1");
            result.put("msg","success");
        } catch (Exception e) {
            result.put("code","0");
            result.put("msg","register error");
        }
        return result;
    }

}
