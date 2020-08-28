package com.yuesun.core.aop;

import com.yuesun.base.security.user.entity.User;
import com.yuesun.core.exception.NotFoundCurrentUserException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class LogAspect {

    private final static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(public * com.yuesun.base.moudle.*.controller..*.*(..))")
    public void module() {
    }

    @Pointcut("execution(public * com.yuesun.base.security.*.controller..*.*(..))")
    public void security() {
    }

    @Pointcut("module() || security()")
    public void init() {
    }

    @Around("init()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        Object object = null;
        //get user
        User user = getCurrentUser();
        if (user == null) {
            throw new NotFoundCurrentUserException();
        }
        object = pjp.proceed();
        return object;
    }


    private User getCurrentUser() {
        Object obj = getHttpServletRequest().getSession().getAttribute("currentUser");
        if (obj == null) {
            return null;
        }
        return (User) obj;
    }

    private HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attrs.getRequest();
        return request;
    }
}
