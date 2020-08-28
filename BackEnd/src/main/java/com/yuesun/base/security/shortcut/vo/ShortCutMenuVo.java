package com.yuesun.base.security.shortcut.vo;

import com.yuesun.base.security.menu.entity.Menu;

public class ShortCutMenuVo extends Menu {
    private boolean isHas = false;

    public boolean isHas() {
        return isHas;
    }

    public void setHas(boolean has) {
        isHas = has;
    }
}
