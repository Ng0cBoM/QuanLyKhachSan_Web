package com.hotel.demo.interceptor;

import com.hotel.demo.constant.AppConstant;
import com.hotel.demo.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(AppConstant.AUTH_SESSION);
        if (user.getRole() == AppConstant.ROLE_GUEST) {
            response.sendRedirect(request.getContextPath()+"/auth/login");
            return false;
        }
        return true;
    }
}