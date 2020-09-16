package com.sai.oabs;

import com.sai.oabs.user.entity.User;
import com.sai.oabs.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestUser {

    @Autowired
    private UserService userService;

    @Test
    public void save(){
        User user = new User();
        user.setUsername("admin");
        user.setPassword("123456");
        user.setAddress("new address");
        user.setEmail("xxx@emali.com");
        userService.save(user);
    }

    @Test
    public void delete(){
        userService.delete(1L);
    }

    @Test
    public void findByUsernameAndPwd(){
        User admin = userService.findByUserNameAndPwd("admin", "123456");
        System.out.println(admin);
    }

    @Test
    public void testFindById(){
        User user = userService.findById(1L);
        System.out.println(user);
    }

}
