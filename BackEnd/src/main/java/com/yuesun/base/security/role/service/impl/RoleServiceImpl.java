package com.yuesun.base.security.role.service.impl;

import com.yuesun.base.security.role.entity.Role;
import com.yuesun.base.security.role.entity.UserRole;
import com.yuesun.base.security.role.repository.RoleRepository;
import com.yuesun.base.security.role.service.RoleService;
import com.yuesun.base.security.role.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
@Service("roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public List<Role> findByUserId(Long userId) {
        return findRoleByUserRoleList(userRoleService.findByUserId(userId));
    }

    @Override
    public Map<Long, String> findRoleNameByUserIds(Long[] userIds) {
        List<UserRole> userRoleList = userRoleService.findByUserIds(userIds);
        List<Role> roleList = findRoleByUserRoleList(userRoleList);

        Map<Long, List<Long>> userIdRoleIdListMap = new HashMap<>();

        for (UserRole userRole : userRoleList) {
            List<Long> roleIdList = userIdRoleIdListMap.get(userRole.getUserId());
            if (roleIdList == null) {
                roleIdList = new ArrayList<>();
            }
            roleIdList.add(userRole.getRoleId());

            userIdRoleIdListMap.put(userRole.getUserId(), roleIdList);
        }

        final String SEPARATOR = " ";
        Map<Long, String> userIdRoleNameMap = new HashMap<>();
        for (Map.Entry<Long, List<Long>> entry : userIdRoleIdListMap.entrySet()) {
            StringBuilder roleNames = new StringBuilder();
            Long userId = entry.getKey();
            List<Long> roleIdList = entry.getValue();
            for (Long roleId : roleIdList) {
                for (Role role : roleList) {
                    if (roleId.equals(role.getId())) {
                        roleNames.append(SEPARATOR + role.getName() + SEPARATOR);
                        break;
                    }
                }
            }
            userIdRoleNameMap.put(userId, roleNames.toString());
        }

        return userIdRoleNameMap;
    }

    private List<Role> findRoleByUserRoleList(List<UserRole> userRoleList) {
        if (CollectionUtils.isEmpty(userRoleList)) {
            return new ArrayList<>();
        }
        Long[] collect = userRoleList.stream().map(UserRole::getRoleId).toArray(Long[]::new);
        return roleRepository.findAllByIdIn(collect);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = false)
    @Override
    public Role save(Role role) {
        roleRepository.save(role);
        return role;
    }

    @Transactional(readOnly = false)
    @Override
    public void delete(Long id) {
        if (userRoleService.isUsed(id)) {
            throw new UnsupportedOperationException("user is used ,can not be delete");
        }
        roleRepository.deleteById(id);
    }

    @Override
    public List<Long> findMenuIdByRoleId(Long id) {
        return roleRepository.selectMenuIdByRoleId(id);
    }

    @Override
    public Role findByRoleName(String roleName) {
        return roleRepository.findAllByName(roleName);
    }


}
