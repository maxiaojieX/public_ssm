package com.ma.controller.interceptor;

import com.ma.entity.Account;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/11/8 0008.
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {

        String url = request.getRequestURI();
        if(url.startsWith("/static/")){
            return true;
        }
        if("".equals(url) || "/".equals(url) || "/login".equals(url)){
            return true;
        }
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        if(account != null){
            return true;
        }else {
            response.sendRedirect("/");
        }

        return false;
    }
}
