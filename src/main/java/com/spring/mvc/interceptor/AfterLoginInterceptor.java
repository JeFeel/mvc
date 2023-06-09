package com.spring.mvc.interceptor;


import com.spring.mvc.util.LoginUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Configuration
// 로그인 이후 비회원 관련 페이지 진입 차단
public class AfterLoginInterceptor implements HandlerInterceptor {

    // 컨트롤러 진입 전에 handling (pre)
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        if(LoginUtil.isLogin(session)){
            // 로그인을 했다면
            response.sendRedirect("/home");
            return false;
        }

        return true;
    }

}
