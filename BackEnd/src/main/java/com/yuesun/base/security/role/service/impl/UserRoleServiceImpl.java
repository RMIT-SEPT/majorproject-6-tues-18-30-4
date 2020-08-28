package com.yuesun.base.security.role.service.impl;

import com.yuesun.base.security.role.entity.UserRole;
import com.yuesun.base.security.role.repository.UserRoleRepository;
import com.yuesun.base.security.role.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;


@Transactional(readOnly = true)
@Service("userRoleService")
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public List<UserRole> findByUserIds(Long[] userIds) {
        Assert.notNull(userIds, "user id can not be null");
        return userRoleRepository.findRoleNameByUserIdIn(userIds);
    }

    @Override
    public List<UserRole> findByUserId(Long userId) {
        return userRoleRepository.findAllByUserId(userId);
    }

    @Override
    public boolean isUsed(Long roleId) {
        List<UserRole> list = userRoleRepository.findAllByRoleId(roleId);
        return !list.isEmpty();
    }

    @Transactional(readOnly = false)
    @Override
    public void saveUserRoles(Long userId, Long[] roleIds) {
        userRoleRepository.deleteByUserId(userId);
        saveUserIdRoleIds(userId,roleIds);
    }

    private void saveUserIdRoleIds(Long userId, Long[] roleIds) {
        List<UserRole> userRoleList = createUserRoleList(userId, roleIds);
        for(UserRole userRole :userRoleList){
            userRoleRepository.save(userRole);
        }
    }

    private List<UserRole> createUserRoleList(Long userId, Long[] roleIds) {
        List<UserRole> userRoleList = new ArrayList<>();
        for (Long roleId : roleIds) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoleList.add(userRole);
        }
        return userRoleList;
    }
}
