package com.spring.mvc.chap05.service;


import com.spring.mvc.chap05.dto.LoginRequestDTO;
import com.spring.mvc.chap05.dto.SignUpRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService service;

    @Test
    @DisplayName("SignUpDTO를 전달하면 회원가입에 성공해야 한다")
    void joinTest(){
        SignUpRequestDTO dto = new SignUpRequestDTO();

        dto.setAccount("kuku");
        dto.setPassword("lalala1234");
        dto.setName("쿠쿠");
        dto.setEmail("aaa@ddd.com");

        service.join(dto);
    }

    @Test
    @DisplayName("계정명이 aaaa인 회원의 로그인시도 시 결과 검증을 상황별로 수행해야 한다")
    void loginTest(){
        //given
        LoginRequestDTO dto = new LoginRequestDTO();

        dto.setAccount("aaaa");
        dto.setPassword("1234@");

        // when
        LoginResult result = service.authenticate(dto);

        //then
        assertEquals(LoginResult.SUCCESS, result);
    }

}