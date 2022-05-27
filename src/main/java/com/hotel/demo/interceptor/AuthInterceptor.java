package com.hotel.demo.interceptor;

import com.hotel.demo.constant.AppConstant;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Objects;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Object object = session.getAttribute(AppConstant.AUTH_SESSION);
        if (Objects.isNull(object)) {
            response.sendRedirect(request.getContextPath()+"/auth/login");
            return false;
        }
        return true;
    }
}