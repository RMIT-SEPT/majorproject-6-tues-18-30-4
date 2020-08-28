package com.yuesun.core.controller;

public abstract class BaseController<T>{

    public abstract T getCurrentUser();
    
    public abstract void setCurrentUser(T t);
    
}
