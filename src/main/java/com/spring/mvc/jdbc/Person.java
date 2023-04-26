package com.spring.mvc.jdbc;
// 1.Person의 정보 변수 선언
// 엔터티: 이 클래스는 데이터베이스 테이블과의 연동을 위한 객체
// DB table의 column과 1:1로 매칭되는 필드를 적어주세요

import lombok.*;


@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private long id;
    private String personName;
    private int personAge;
}