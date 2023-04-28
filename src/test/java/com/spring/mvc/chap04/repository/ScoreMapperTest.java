package com.spring.mvc.chap04.repository;


import com.spring.mvc.chap04.entity.Score;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.spring.mvc.chap04.entity.Grade.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ScoreMapperTest {

    @Autowired
    ScoreMapper mapper;

    @Test
    @DisplayName("맵핑으로 성적정보를 등록할 수 있어야 한다")
    void saveMapperTest(){

        Score score = Score.builder()
                .name("둘리")
                .kor(80)
                .eng(60)
                .math(70)
                .total(210)
                .avg(70.0)
                .grade(C)
                .build();
        boolean flag = mapper.save(score);

        assertTrue(flag);

    }

}