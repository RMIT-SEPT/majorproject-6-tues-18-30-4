package com.yuesun.base.security.shortcut.repository;

import com.yuesun.base.security.shortcut.entity.ShortCut;
import com.yuesun.core.repository.BaseRepository;

import java.util.List;

public interface ShortCutRepository extends BaseRepository<ShortCut,Long> {

    void deleteByUserId(Long userId);

    List<ShortCut> findAllByUserId(Long userId);

}
