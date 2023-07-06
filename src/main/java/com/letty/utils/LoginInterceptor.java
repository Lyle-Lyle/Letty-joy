package com.letty.utils;

import com.letty.dto.UserDTO;
import com.letty.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // get user from session
        HttpSession session = request.getSession();
        Object user = session.getAttribute("user");

        // if user matches
        if (user == null) {
            response.setStatus(401);
            return false;
        }
        // store user in ThreadLocal
        UserHolder.saveUser((UserDTO) user);
        // 放行
        return true;

    }


    // 在业务执行之后，视图渲染之前执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // remove user in ThreadLocal
        UserHolder.removeUser();
    }
}
