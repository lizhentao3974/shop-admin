package com.fh.shop.admin.interceptor;

import com.fh.shop.admin.common.SystemConstant;
import com.fh.shop.admin.po.user.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UserInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SystemConstant.USER_LOGIN);
        if (user != null) {
            return true;
        } else {

            String header = request.getHeader("X-Requested-With");
            if (StringUtils.isNotEmpty(header) && header.equals("XMLHttpRequest")) {
                response.setHeader("fh_ajax_timeout", "timeout");
            } else {
                response.sendRedirect(SystemConstant.LOGIN_JSP);
            }
            return false;
        }
    }
}
