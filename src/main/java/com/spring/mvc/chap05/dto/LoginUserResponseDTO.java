package com.spring.mvc.chap05.dto;


import lombok.*;


@Setter @Getter
@NoArgsConstructor @ToString @EqualsAndHashCode
@AllArgsConstructor
@Builder
public class LoginUserResponseDTO {

    private String account;
    private String nickName;
    private String email;
    private String auth; //권한 부여 위해서
    private String profile; //header.jsp에 프사 넘겨줘야 하기 때문에
}
