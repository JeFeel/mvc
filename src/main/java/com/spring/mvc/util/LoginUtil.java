package com.spring.mvc.util;

//회원 인증 인가 관련 상수와 메서드를 가진 객체


import com.spring.mvc.chap05.dto.LoginUserResponseDTO;

import javax.servlet.http.HttpSession;
import java.util.stream.Stream;


public class LoginUtil {

    //로그인 세션 키
    public static final String LOGIN_KEY = "login";

    // 로그인 여부 확인
    public static boolean isLogin(HttpSession session){
        return session.getAttribute(LOGIN_KEY) != null;
        // null이면 로그인을 안 했다는 뜻
    }

    //로그인한 사람의 계정명 반환 메서드
    public static String getCurrentLoginMemberAccount(HttpSession session){
        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        return loginUserInfo.getAccount();
    }
}
