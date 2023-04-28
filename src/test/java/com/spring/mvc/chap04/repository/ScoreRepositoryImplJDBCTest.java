package com.spring.mvc.chap04.repository;

import com.spring.mvc.chap04.entity.Score;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class ScoreRepositoryImplJDBCTest {

    @Autowired
    ScoreRepositoryImpl repository;

    @Test
    void saveJDBCTest(){
        Score sc = new Score();
        sc.setName("흰둥이");
        sc.setKor(70);
        sc.setEng(60);
        sc.setMath(50);

        repository.save(sc);
    }
}