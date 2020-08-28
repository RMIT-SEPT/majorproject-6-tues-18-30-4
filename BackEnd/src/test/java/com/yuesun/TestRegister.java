package com.yuesun;

import com.yuesun.base.security.user.entity.User;
import com.yuesun.base.security.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestRegister {

    @Autowired
    private UserService userService;

    @Test
    public void testSave(){
        User user = new User();
        user.setLoginName("user123456");
        user.setPassword("123456");
        user.setName("sai");
        user.setPhone("1385937295");
        user.setAddress("New York");
        userService.save(user);
    }

    @Test
    public void testFindAll(){
        List<User> all = userService.findAll();
        System.out.println(all);
    }

}
