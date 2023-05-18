package com.spring.mvc.chap05.dto.sns;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;


@Setter @Getter
@ToString
public class KakaoUserDTO {

    private long id;

    @JsonProperty("connected-at")
    private LocalDateTime connectedAt;

    @JsonProperty("kakao_account")
    private KakaoUserDTO.kakaoAccount kakaoAccount;

    @Setter @Getter @ToString
    public static class kakaoAccount {

        private String email;
        private Profile profile;

        @Setter @Getter @ToString
        public static class Profile{

            private String nickname;

            @JsonProperty("profile_image_url")
            private String profileImageUrl;
        }
    }


}
