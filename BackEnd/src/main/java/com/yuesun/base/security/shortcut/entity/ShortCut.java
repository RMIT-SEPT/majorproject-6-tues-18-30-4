package com.yuesun.base.security.shortcut.entity;

import com.yuesun.core.entity.IdEntity;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "shortcut")
public class ShortCut extends IdEntity {
    private Long userId;
    private Long menuId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }
}
