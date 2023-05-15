package com.spring.mvc.chap05.service;


import com.spring.mvc.chap05.dto.AutoLoginDTO;
import com.spring.mvc.chap05.dto.LoginRequestDTO;
import com.spring.mvc.chap05.dto.LoginUserResponseDTO;
import com.spring.mvc.chap05.dto.SignUpRequestDTO;
import com.spring.mvc.chap05.entity.Member;
import com.spring.mvc.chap05.repository.MemberMapper;
import com.spring.mvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;

import static com.spring.mvc.chap05.service.LoginResult.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder encoder;

    // 회원가입 처리 서비스
    public boolean join(final SignUpRequestDTO dto) {

        // dto를 entity로 변환
        Member member = Member.builder()
                .account(dto.getAccount())
                .email(dto.getEmail())
                .name(dto.getName())
                .password(encoder.encode(dto.getPassword())).build();


        // mapper에게 회원정보 전달해서 저장명령
        return memberMapper.save(member);
    }

    //중복검사 서비스 처리
    public boolean checkSignUpValue(String type, String keyword) {
        int flagNum = memberMapper.isDuplicate(type, keyword);
        // 중복이면 1, 아니면 0
        return flagNum == 1;
    }

    //로그인 검증
    public LoginResult authenticate(LoginRequestDTO dto, HttpSession session, HttpServletResponse response) {
        Member foundMember = memberMapper.findMember(dto.getAccount());

        if (foundMember == null) {
            log.info("{} - 회원이 아닙니다", dto.getAccount());
            return NO_ACC;
        }

        //비밀번호 일치 확인
        if (!encoder.matches(dto.getPassword(), foundMember.getPassword())) {
            log.info("비밀번호가 일치하지 않습니다");
            return NO_PW;
        }

        // 자동 로그인 체크 여부 확인
        if (dto.isAutoLogin()) {
            // 1. 쿠키 생성 - 쿠기값에 세션아이디를 저장
            Cookie autoLoginCookie = new Cookie(LoginUtil.AUTO_LOGIN_COOKIE, session.getId());
            // 2. 쿠키 세팅 - 수명이랑 사용경로
            int limitTime = 60 * 60 * 24 * 90; //90일
            autoLoginCookie.setMaxAge(limitTime);
            autoLoginCookie.setPath("/"); //전체 경로

            // 3. 쿠키를 클라이언트에 응답전송
            response.addCookie(autoLoginCookie);

            // 4. DB에도 쿠키에 저장되는 value와 수명을 저장
            memberMapper.saveAutoLogin(
                    AutoLoginDTO.builder()
                            .sessionId(session.getId())
                            .account(dto.getAccount())
                            .limitTime(LocalDateTime.now().plusDays(90))
                            .build()
            );
        }
        log.info("{}님 로그인에 성공하였습니다", foundMember.getAccount());
        return SUCCESS;
    }

    public void maintainLoginState(HttpSession session, String account) {
        // 로그인이 성공하면 세션에 로그인한 회원의 정보들을 저장
        /*
         * 로그인시 클라이언트에게 전달할 회원정보
         * -닉네임
         * -프로필사지
         * -마지막 로그인 시간
         * */
        // 현재 로그인 사람 모든 정보

        Member member = getMember(account);

        //현재 로그인한 사람의 화면에 보여줄 일부정보
        LoginUserResponseDTO dto = LoginUserResponseDTO.builder()
                .account(member.getAccount())
                .nickName(member.getName())
                .email(member.getEmail())
                .auth(member.getAuth().toString())
                .build();
        // 정보를 세션에 저장
        session.setAttribute(LoginUtil.LOGIN_KEY, dto);
        // 세션의 수명을 설정
        session.setMaxInactiveInterval(60 * 60);  //1시간

    }

    // 멤버 정보를 가져오는 서비스기능
    public Member getMember(String account) {
        return memberMapper.findMember(account);
    }

    public void autoLoginClear(HttpServletRequest request, HttpServletResponse response) {
        // 1. 자동로그인 쿠키를 가져온다
        Cookie c = WebUtils.getCookie(request, LoginUtil.AUTO_LOGIN_COOKIE);

        // 2. 쿠키를 삭제한다 -> 쿠키의 수명을 0초로 만들고 다시 client에게 응답 (완전 삭제는 없다)
        if (c != null) {
            c.setMaxAge(0);
            c.setPath("/"); // 삭제한 뒤에도 다시 쿠키 경로 설정
            response.addCookie(c);

            // 3. 데이터베이스에도 자동로그인을 해제한다.
            memberMapper.saveAutoLogin(
                    AutoLoginDTO.builder()
                            .sessionId("none")
                            .limitTime(LocalDateTime.now())
                            .account(LoginUtil.getCurrentLoginMemberAccount(request.getSession()))
                            .build()
            );


        }

    }
}
