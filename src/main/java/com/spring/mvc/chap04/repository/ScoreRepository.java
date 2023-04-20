package com.spring.mvc.chap04.repository;

// 역할 명세 (추상화):
// 성적 정보를 저장, 추가, 삭제, 수정
// 어디서 저장? 조회? 삭제?

import com.spring.mvc.chap04.entity.Score;

import java.util.List;


public interface ScoreRepository {

    // 성적 정보 전체 목록 조회
    List<Score> findAll(); //일반 조회

    default List<Score> findAll(String sort) {  //정렬조회
        return null;
    }; //default 주면 오버라이딩 강제x

    // 성정 정보 등록
    boolean save(Score score);

    // 성적 정보 1개 삭제
    boolean deleteByStuNum(int stuNum);

    // 성적 정보 개별 조회
    Score findByStuNum(int stuNum);

}
