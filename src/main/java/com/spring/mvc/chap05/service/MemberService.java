package com.spring.mvc.chap05.service;


import com.spring.mvc.chap05.dto.LoginRequestDTO;
import com.spring.mvc.chap05.dto.SignUpRequestDTO;
import com.spring.mvc.chap05.entity.Member;
import com.spring.mvc.chap05.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.spring.mvc.chap05.service.LoginResult.*;


@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder encoder;

    // 회원가입 처리 서비스
    public boolean join(final SignUpRequestDTO dto){

        // dto를 entity로 변환
        Member member  = Member.builder()
                .account(dto.getAccount())
                .email(dto.getEmail())
                .name(dto.getName())
                .password(encoder.encode(dto.getPassword())).build();


        // mapper에게 회원정보 전달해서 저장명령
        return  memberMapper.save(member);
    }

    //중복검사 서비스 처리
    public boolean checkSignUpValue(String type, String keyword) {
        int flagNum = memberMapper.isDuplicate(type, keyword);
        // 중복이면 1, 아니면 0
        return flagNum == 1;
    }

    //로그인 검증
    public LoginResult authenticate(LoginRequestDTO dto) {
        Member foundMember = memberMapper.findMember(dto.getAccount());

        if (foundMember == null) {
            log.info("{} - 회원이 아닙니다", dto.getAccount());
            return NO_ACC;
        }

        //비밀번호 일치 확인
        if(!encoder.matches(dto.getPassword(), foundMember.getPassword())){
            log.info("비밀번호가 일치하지 않습니다");
            return NO_PW;
        }
        log.info("{}님 로그인에 성공하였습니다", foundMember.getAccount());
        return SUCCESS;
    }
}
