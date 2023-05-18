package com.spring.mvc.chap04.repository;


import com.spring.mvc.chap04.entity.Score;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.spring.mvc.chap04.entity.Grade.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScoreSpringRepositoryTest {
    @Autowired
    ScoreSpringRepository repository;

    @Test
    void saveScoreTest(){
        //given
        Score s = new Score();
        s.setName("하이호");
        s.setKor(50);
        s.setEng(50);
        s.setMath(50);
        s.setTotal(150);
        s.setAvg(50);
        s.setGrade(C);
        //when
        boolean flag = repository.save(s);
        //then
        assertTrue(flag);



    }
}