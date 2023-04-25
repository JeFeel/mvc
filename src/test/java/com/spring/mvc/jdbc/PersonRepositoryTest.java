package com.spring.mvc.jdbc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonRepositoryTest {

    @Autowired
    PersonRepository repository;

    @Test
    @DisplayName("사람의 이름과 나이 정보를 " +
            "DB person table에 잘 삽입해야 한다.")
    void saveTest() {
        // given
        Person p = new Person();
        p.setPersonName("춘삼이");
        p.setPersonAge(25);
        // when
        repository.save(p);
        // then

    }

    @Test
    @DisplayName("사람의 이름과 나이 정보를 " +
            "DB person table에 잘 수정해야 한다.")
    void updateTest() {
        // given
        Person p = new Person();
        p.setPersonName("수정수정");
        p.setPersonAge(99);
        p.setId(1L);
        // when
        repository.update(p);
        // then

    }

    @Test
    @DisplayName("사람의 이름과 나이 정보를 " +
            "DB person table에 잘 삭제해야 한다.")
    void removeTest() {
        // given
        long id = 2L;
        // when
        repository.remove(id);
        // then

    }




    @Test
    void findAllTest(){
        List<Person> people = repository.findAll();
        for (Person p : people) {
            System.out.println("p = " + p);
        }
    }

    @Test
    void findOneTest() {
        Person p = repository.findOne(4L);
        System.out.println("p = " + p);
    }
}