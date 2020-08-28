package com.yuesun.base.security.shortcut.service.impl;

import com.yuesun.base.security.menu.entity.Menu;
import com.yuesun.base.security.shortcut.entity.ShortCut;
import com.yuesun.base.security.shortcut.repository.ShortCutRepository;
import com.yuesun.base.security.shortcut.service.ShortCutService;
import com.yuesun.base.security.shortcut.vo.ShortCutMenuVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional( readOnly = true)
@Service("shortCutService")
public class ShortCutServiceImpl implements ShortCutService {

    @Autowired
    private ShortCutRepository shortCutRepository;

    @Override
    @Transactional( readOnly = false)
    public void save(Long userId, Long[] menuIds) {
        shortCutRepository.deleteByUserId(userId);
        saveBatch(userId,menuIds);
    }

    @Override
    public List<ShortCut> findByUserId(Long userId) {
        return shortCutRepository.findAllByUserId(userId);
    }

    @Override
    public List<ShortCutMenuVo> findShortCutMenuVo(Long userId, List<Menu> allMenuList) {
        return null;
    }

    private void saveBatch(Long userId,Long[] menuIds){
        for (Long menuId : menuIds) {
            ShortCut shortCut = new ShortCut();
            shortCut.setUserId(userId);
            shortCut.setMenuId(menuId);
            shortCutRepository.save(shortCut);
        }
    }

    private List<Menu> getHasIndexUrlMenuList(List<Menu> allMenuList){
        return allMenuList.stream()
                .filter(t-> StringUtils.isNotBlank(t.getName()))
                .collect(Collectors.toList());
    }

    private List<ShortCutMenuVo> packageList(List<Menu> hasIndexUrlMenuList,List<ShortCut> shortCutList){
        List<ShortCutMenuVo> shortCutMenuVoList = transformShortCutMenuVoList(hasIndexUrlMenuList);
        shortCutMenuVoList.stream().forEach(t->{
            shortCutList.stream().forEach(f->{
                if(f.getMenuId().equals(t.getId())){
                    t.setHas(true);
                }
            });
        });
        return shortCutMenuVoList;
    }

    private List<ShortCutMenuVo> transformShortCutMenuVoList(List<Menu> hasIndexUrlMenuList){
        List<ShortCutMenuVo> shortCutMenuVoList = new ArrayList<>();
        hasIndexUrlMenuList.stream().forEach(t->{
            ShortCutMenuVo scmv = new ShortCutMenuVo();
            BeanUtils.copyProperties(t,scmv);
            shortCutMenuVoList.add(scmv);
        });
        return shortCutMenuVoList;
    }

}
