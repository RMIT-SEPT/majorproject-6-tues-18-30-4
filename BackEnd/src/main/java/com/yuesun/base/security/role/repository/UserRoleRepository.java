package com.yuesun.base.security.role.repository;

import com.yuesun.base.security.role.entity.UserRole;
import com.yuesun.core.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends BaseRepository<UserRole, Long> {
    @Modifying
    @Query("delete from UserRole where userId = :userId")
    void deleteByUserId(@Param("userId") Long userId);

    List<UserRole> findRoleNameByUserIdIn(Long[] userIds);
    List<UserRole> findAllByUserId(Long userId);
    List<UserRole> findAllByRoleId(Long roleId);

}
