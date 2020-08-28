package com.yuesun.core.controller;
import com.yuesun.base.security.menu.entity.Menu;
import com.yuesun.base.security.role.entity.Role;
import com.yuesun.base.security.user.entity.User;
import com.yuesun.core.entity.HttpResult;
import com.yuesun.core.entity.Page;
import com.yuesun.core.exception.NotFoundAnyMenusException;
import com.yuesun.core.exception.NotFoundAnyRolesException;
import com.yuesun.core.exception.NotFoundCurrentUserException;
import com.yuesun.core.util.HttpResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AdminController extends BaseController<User> {

    protected final static String SESSION_CURRENT_USER = "currentUser";
    private final static String SESSION_CURRENT_ROLE = "currentRole";
    private final static String SESSION_CURRENT_MENU = "currentMenu";

    @Override
    public void setCurrentUser(User user) {
        HttpServletRequest request = getHttpServletRequest();
        request.getSession().setAttribute(SESSION_CURRENT_USER, user);
    }

    @Override
    public User getCurrentUser() {
        Object obj = getHttpSession().getAttribute(SESSION_CURRENT_USER);
        if (obj == null) {
            throw new NotFoundCurrentUserException();
        }
        return (User) obj;
    }

    public void setCurrentRoles(List<Role> roleList) {
        HttpSession session = getHttpSession();
        session.setAttribute(SESSION_CURRENT_ROLE, roleList);
    }

    @SuppressWarnings("unchecked")
    public List<Role> getCurrentRoles() {
        Object obj = getHttpSession().getAttribute(SESSION_CURRENT_ROLE);
        if (obj == null) {
            throw new NotFoundAnyRolesException();
        }
        return (List<Role>) obj;
    }

    public void setCurrentMenus(List<Menu> menuList) {
        HttpSession session = getHttpSession();
        session.setAttribute(SESSION_CURRENT_MENU, menuList);
    }

    @SuppressWarnings("unchecked")
    public List<Menu> getCurrentMenus() {
        Object obj = getHttpSession().getAttribute(SESSION_CURRENT_MENU);
        if (obj == null) {
            throw new NotFoundAnyMenusException();
        }
        return (List<Menu>) obj;
    }

    private HttpSession getHttpSession() {
        HttpServletRequest request = getHttpServletRequest();
        HttpSession session = request.getSession();
        return session;
    }

    private HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();
        return request;
    }

    protected HttpResult returnSuccess(String ocd) {
        return HttpResultUtil.createHttpResultSuccess(ocd);
    }

    protected HttpResult returnValidationError(String ocd) {
        return HttpResultUtil.createHttpResultValidationError(ocd);
    }

    protected HttpResult returnSystemError(String ocd) {
        return HttpResultUtil.createSystemError(ocd);
    }

    protected <T> Page<T> buildPage(ServletRequest request) {
        int pageNo = getPageNoByRequest(request);
        int pageSize = getPageSizeByRequest(request);
        return new Page<T>(pageNo, pageSize);
    }

    protected PageRequest buildDataPage(ServletRequest request) {
        int pageNo = getPageNoByRequest(request);
        int pageSize = getPageSizeByRequest(request);
        return PageRequest.of(pageNo - 1, pageSize);
    }

    private int getPageNoByRequest(ServletRequest request) {
        try {
            return getParameterInt(request, "page");
        } catch (NullPointerException e) {
            return 1;
        }
    }

    private int getPageSizeByRequest(ServletRequest request) {
        try {
            return getParameterInt(request, "rows");
        } catch (NullPointerException e) {
            return Page.DEFAULT_PAGE_SIZE;
        }
    }

    private int getParameterInt(ServletRequest request, String name) {
        String value = request.getParameter(name);
        if (StringUtils.isNumeric(value)) {
            return Integer.parseInt(value);
        }
        throw new NullPointerException("error");
    }

    protected static <T> Page<T> getPage(org.springframework.data.domain.Page<T> pageInfo) {
        Page<T> page = new Page<T>(pageInfo.getNumber() + 1, pageInfo.getSize());
        page.setRows(pageInfo.getContent());
        page.setTotal(pageInfo.getTotalElements());
        return page;
    }

}
