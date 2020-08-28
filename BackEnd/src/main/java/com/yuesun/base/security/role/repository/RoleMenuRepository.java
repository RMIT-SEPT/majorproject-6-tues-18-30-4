package com.yuesun.base.security.role.repository;

import com.yuesun.base.security.role.entity.RoleMenu;
import com.yuesun.core.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuRepository extends BaseRepository<RoleMenu, Long> {
   List<RoleMenu> findRoleMenusByRoleIdIn(Long[] roleIds);

   @Modifying
   @Query("delete from RoleMenu where roleId = :roleId")
   void deleteByRoleId(@Param("roleId") Long roleId);
}
