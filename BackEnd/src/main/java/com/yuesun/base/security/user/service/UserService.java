package com.yuesun.base.security.user.service;

import com.yuesun.base.security.user.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface UserService {

    User save(User user);

    void update(User user);

    User findById(Long id);

    void deleteById(Long id);

    List<User> findAll();

    void resetPassword(Long userId, String password);

    User findByLoginNameAndPasswrod(String loginName, String password);

    User findByLoginName(String loginName);

    Page<User> findByPage(PageRequest pageRequest);

}
