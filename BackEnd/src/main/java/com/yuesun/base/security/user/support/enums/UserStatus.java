package com.yuesun.base.security.user.support.enums;


public enum UserStatus {
    Y(0),
    N(1);
    private int value;

    UserStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
