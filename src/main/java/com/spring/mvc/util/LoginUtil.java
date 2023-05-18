package com.spring.mvc.util;

//회원 인증 인가 관련 상수와 메서드를 가진 객체


import com.spring.mvc.chap05.dto.LoginUserResponseDTO;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.stream.Stream;


public class LoginUtil {

    //로그인 세션 키
    public static final String LOGIN_KEY = "login";
    // 자동로그인 쿠키 이름
    public static final String AUTO_LOGIN_COOKIE = "auto";

    // 로그인 여부 확인
    public static boolean isLogin(HttpSession session){
        return session.getAttribute(LOGIN_KEY) != null;
        // null이면 로그인을 안 했다는 뜻
    }

    // 자동 로그인 여부 확인
    public static boolean isAutoLogin(HttpServletRequest request){
        return WebUtils.getCookie(request, AUTO_LOGIN_COOKIE)!=null;
    }

    //로그인한 사람의 계정명 반환 메서드
    public static String getCurrentLoginMemberAccount(HttpSession session){
        LoginUserResponseDTO loginUserInfo = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);
        return loginUserInfo.getAccount();
    }

    // 관리자인지 확인해주는 메서드
    public static boolean isAdmin(HttpSession session){
        LoginUserResponseDTO loginUser = (LoginUserResponseDTO) session.getAttribute(LOGIN_KEY);

        return loginUser.getAuth().equals("ADMIN");
    }

    // 본인이 쓴 글인지 확인해주는 메서드
    // 로그인 계정명과 실제 게시물 계정명 비교가 필요
    public static boolean isMine(HttpSession session, String targetAccount) {
        return targetAccount.equals(getCurrentLoginMemberAccount(session));
    }


}
