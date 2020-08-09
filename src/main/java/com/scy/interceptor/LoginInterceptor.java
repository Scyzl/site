package com.scy.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 *
 * @Author Scy
 * @Date 2020/8/7 15:31
 * @Version 1.0
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // 通过查看session来确定是否已登录
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("/admin/login");
            return false;
        }
        return true;
    }
}
