package com.yuesun.base.security.user.service.impl;

import com.yuesun.base.security.user.entity.User;
import com.yuesun.base.security.user.repository.UserRepository;
import com.yuesun.base.security.user.service.UserService;
import com.yuesun.base.security.user.support.enums.UserStatus;
import com.yuesun.core.util.SecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("userService")
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = false)
    @Override
    public User save(User user) {
        setUserCreateTime(user);
        if(StringUtils.isNotBlank(user.getPassword())){
            setUserDefaultPassword(user);
        }else{
            String password = encodeMd5Password(user.getPassword());
            user.setPassword(password);
        }
        return userRepository.save(user);
    }

    private void setUserCreateTime(User user) {
        if (user.getCreateTime() == null) {
            user.setCreateTime(new Date());
        }
    }

    private void setUserDefaultPassword(User user) {
        String defaultPassword = "123456";
        String password = encodeMd5Password(defaultPassword);
        user.setPassword(password);
    }

    @Transactional(readOnly = false)
    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Transactional(readOnly = false)
    @Override
    public User findById(Long id) {
        return userRepository.getOne(id);
    }

    @Transactional(readOnly = false)
    @Override
    public void deleteById(Long id) {
        Assert.notNull(id, "id can not be null");
        userRepository.deleteUserById(id, UserStatus.N.getValue());
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAllByStatus(UserStatus.Y.getValue());
    }

    @Transactional(readOnly = false)
    @Override
    public void resetPassword(Long userId, String password) {
        Assert.noNullElements(new Object[]{userId, password}, "id and password can not be null");
        password = encodeMd5Password(password);
        userRepository.restNewPassWord(userId, password);
    }

    @Override
    public User findByLoginNameAndPasswrod(String loginName, String password) {
        password = encodeMd5Password(password);
        List<User> userList = userRepository.findByLoginNameAndPasswordAndStatusEquals(loginName, password, UserStatus.Y.getValue());
        if (userList.size() <= 0) {
            return null;
        }
        return userList.get(0);
    }

    @Override
    public User findByLoginName(String loginName) {
        List<User> list = userRepository.findByLoginNameAndStatus(loginName, UserStatus.Y.getValue());
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Page<User> findByPage(PageRequest pageRequest) {

        Page<User> userPage = userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicatesList = new ArrayList<>();
                Predicate statusPredicate = criteriaBuilder.equal(root.get("status"), UserStatus.Y.getValue());

                predicatesList.add(statusPredicate);
                Predicate[] p = new Predicate[predicatesList.size()];
                return criteriaBuilder.and(predicatesList.toArray(p));
            }
        }, PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), Sort.Direction.ASC, "id"));
        return userPage;
    }

    private String encodeMd5Password(String password) {
        try {
            return SecurityUtil.encodeMd5(password);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
