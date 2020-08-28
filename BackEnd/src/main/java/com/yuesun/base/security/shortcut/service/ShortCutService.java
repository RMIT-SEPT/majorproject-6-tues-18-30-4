package com.yuesun.base.security.shortcut.service;

import com.yuesun.base.security.menu.entity.Menu;
import com.yuesun.base.security.shortcut.entity.ShortCut;
import com.yuesun.base.security.shortcut.vo.ShortCutMenuVo;

import java.util.List;

public interface ShortCutService {
    void save(Long userId, Long[] menuIds);
    List<ShortCut> findByUserId(Long userId);
    List<ShortCutMenuVo> findShortCutMenuVo(Long userId, List<Menu> allMenuList);

}
