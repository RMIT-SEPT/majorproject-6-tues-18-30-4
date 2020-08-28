package com.yuesun.base.security.user.repository;

import com.yuesun.base.security.user.entity.User;
import com.yuesun.core.repository.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends BaseRepository<User, Long> {
    @Modifying
    @Query("update User set status = :status where id = :id")
    void deleteUserById(@Param("id") Long id, @Param("status") int status);

    List<User> findAllByStatus(int status);

    @Modifying
    @Query("update User set password = :password where id = :userId")
    void restNewPassWord(@Param("userId") Long userId, @Param("password") String password);

    List<User> findByLoginNameAndPasswordAndStatusEquals(String loginName, String password, int status);

    List<User> findByLoginNameAndStatus(String loginName, int status);

}