package com.yuesun.base.security.menu.repository;

import com.yuesun.base.security.menu.entity.Menu;
import com.yuesun.core.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuRepository extends BaseRepository<Menu, Long> {
    List<Menu> findMenusByIdIn(Long[] ids);
}
