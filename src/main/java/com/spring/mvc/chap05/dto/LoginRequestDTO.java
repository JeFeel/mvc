package com.spring.mvc.chap05.dto;


import lombok.*;


@Setter @Getter
@NoArgsConstructor
@ToString @EqualsAndHashCode
public class LoginRequestDTO {

    private String account;
    private String password;
    private boolean autoLogin; //자동로그인 체크여부

    //dto와 jsp의 name은 같아야함


}
