package com.spring.mvc.interceptor;


import com.spring.mvc.chap05.entity.Member;
import com.spring.mvc.chap05.repository.MemberMapper;
import com.spring.mvc.chap05.service.MemberService;
import com.spring.mvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;

import static com.spring.mvc.util.LoginUtil.*;


@Configuration
@RequiredArgsConstructor
public class AutoLoginInterceptor implements HandlerInterceptor {

    private final MemberMapper memberMapper;
    private final MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 1. 사이트 재방문했을때 자동로그인 쿠키 탐색
        Cookie c = WebUtils.getCookie(request, AUTO_LOGIN_COOKIE);// Cookie 배열에서 iter 자동으로 처리해줌

        if (c != null) {
            // 2. 쿠키에 저장된 세션아이디를 읽기
            String sessionId = c.getValue();

            // 3. DB에서 세션아이디로 회원정보를 조회한다.
            Member foundMember = memberMapper.findMemberByCookie(sessionId);

            // 4. 회원이 조회됐고 자동로그인 쿠기의 만료일 이전이라면
            if (foundMember != null && LocalDateTime.now().isBefore(foundMember.getLimitTime())) {
                // 5. 로그인 처리를 해준다 (Service로 넘어감)
                memberService.maintainLoginState(request.getSession(), foundMember.getAccount());
            }
        }
        return true;
    }
}
