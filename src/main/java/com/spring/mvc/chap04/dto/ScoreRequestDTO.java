package com.spring.mvc.chap04.dto;


import lombok.*;


@Setter @Getter @ToString
@NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode
public class ScoreRequestDTO {

    private String name; //학생 이름
    private int kor, eng, math; //국 영 수 점수


}
