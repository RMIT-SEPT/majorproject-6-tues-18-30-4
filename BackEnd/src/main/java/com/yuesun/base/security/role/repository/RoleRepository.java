package com.yuesun.base.security.role.repository;


import com.yuesun.base.security.role.entity.Role;
import com.yuesun.core.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RoleRepository extends BaseRepository<Role, Long> {

    List<Role> findAllByIdIn(Long[] id);

    @Query("select menuId from RoleMenu where roleId=:id")
    List<Long> selectMenuIdByRoleId(@Param("id") Long id);

    Role findAllByName(String name);
}
