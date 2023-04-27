package com.spring.mvc.etc;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void tttt() {

        Employee.EmployeeBuilder builder = Employee.builder();
        Employee.EmployeeBuilder eb1 = builder.empName("홍길동");
        Employee.EmployeeBuilder eb2 = eb1.position("과장");
        Employee employee = eb2.build();

        Employee e = Employee.builder()
                .position("대리")
                .empName("홍길동")
                .hireDate(LocalDate.of(2019, 3, 15))
                .build();

        System.out.println("e = " + e);

        Actor actor = Actor.builder()
                .actorName("장동건")
                .actorAge(40)
                .hasPhone(false)
                .build();
        System.out.println("actor = " + actor);

    }

}