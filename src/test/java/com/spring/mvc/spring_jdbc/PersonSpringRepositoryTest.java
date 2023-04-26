package com.spring.mvc.spring_jdbc;


import com.spring.mvc.jdbc.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonSpringRepositoryTest {
    @Autowired
    PersonSpringRepository repository;

    @Test
    void savePersonTest(){
        //given
        Person p = new Person();
        p.setPersonName("스프링김");
        p.setPersonAge(2);
        //when
        repository.savePerson(p);
    }

    @Test
    void removePersonTest(){
        //given
        long id = 4L;
        //when
        repository.removePerson(id);
    }

    @Test
void  modifyPersonTest(){
        //given
        Person p = new Person();
        p.setId(3);
        p.setPersonName("헤이호");
        p.setPersonAge(28);
        //when
        boolean flag = repository.modifyPerson(p);
        //then
        assertTrue(flag);
    }

    @Test
    void findAllTest(){
        List<Person> people = repository.findAll();
        for (Person p : people) {
            System.out.println("p = " + p);
        }
    }

    @Test
    void findOneTest(){
        Person p = repository.findOne(3L);
        System.out.println("p = " + p);
        assertEquals("헤이호", p.getPersonName());
    }
}