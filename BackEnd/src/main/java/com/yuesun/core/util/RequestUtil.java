package com.yuesun.core.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public final class RequestUtil {
    private RequestUtil() {
    }

    public static HttpServletRequest getHttpServletRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public static HttpServletResponse getHttpServletResponse() {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        return response;
    }

    public static HttpSession getHttpSession() {
        HttpServletRequest request = getHttpServletRequest();
        if (request != null) {
            return request.getSession();
        }
        return null;
    }

    public static ServletContext getServletContext() {
        ServletContext context = ContextLoader.getCurrentWebApplicationContext().getServletContext();
        return context;
    }

    /**
     * 返回服务器路径
     * @return
     */
    public static String getServerPath() {
        HttpServletRequest request = getHttpServletRequest();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(request.getScheme());
        stringBuilder.append("://");
        stringBuilder.append(request.getServerName());
        stringBuilder.append(":");
        stringBuilder.append(request.getServerPort());
        stringBuilder.append(request.getContextPath());
        return stringBuilder.toString();
    }
}
