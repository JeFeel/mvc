package com.spring.mvc.chap05.dto;

import lombok.*;


@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
public class BoardRewriteRequestDTO {

    private String title;
    private String content;
    private int boardNo;
}