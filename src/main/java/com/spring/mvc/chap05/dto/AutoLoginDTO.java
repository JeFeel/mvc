package com.spring.mvc.chap05.dto;


import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AutoLoginDTO {
    // 자동 로그인 쿠키 저장 위해 필요한 값들

    private String sessionId;
    private LocalDateTime limitTime;
    private String account;
}
