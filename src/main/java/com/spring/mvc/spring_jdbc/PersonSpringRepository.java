package com.spring.mvc.spring_jdbc;


import com.spring.mvc.jdbc.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class PersonSpringRepository {

    // 스프링 JDBC 방식 활용 - 데이터베이스 접속 설정 정보를
    // 설정파일을 통해 불러와서 사용
    private final JdbcTemplate jdbcTemplate;
    
    // 저장 기능
    public void savePerson(Person p){
        String sql = "INSERT INTO person " +
                " (person_name, person_age) " +
                " VALUES (?, ?) ";
        jdbcTemplate.update(sql, p.getPersonName(), p.getPersonAge());

    }
    //삭제 기능
    public void removePerson(long id){
        String sql = "DELETE FROM person WHERE id = ?  ";
        jdbcTemplate.update(sql,id);
    }

    //수정 기능
    public boolean modifyPerson(Person p){
        String sql = " UPDATE person SET person_name = ?, person_age = ? " +
                " WHERE id = ? ";
        int result = jdbcTemplate.update(sql, p.getPersonName(), p.getPersonAge(), p.getId());
        return result==1;
    }
    
    // 전제 조회 기능☆☆☆
    public List<Person> findAll(){
        String sql = "SELECT * FROM person";
//        List<Person> personList = jdbcTemplate.query(sql, (rs, rowNum) ->  new Person(rs)); //람다식
////                Person p = new Person(rs);
//////                p.setId(rs.getLong("id"));
//////                p.setPersonName(rs.getString("person_name"));
//////                p.setPersonAge(rs.getInt("person_age"));
////                //여기다 다 쓰면 거추장스러우니 Person 클래스에 넘겨주자
////
////                return p;
//
////            return new Person(rs);  //인라인화 -> 람다식으로 줄여줌
//
//
//        return personList; //-> 마무리로 인라인화


        //RowMapper를 사용하면, 원하는 형태의 결과값을 반환할 수 있다.
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Person(rs));
    }
    
    
    // 개별 조회 기능
    public Person findOne(long id){
        String sql="SELECT * FROM person WHERE id=? ";
        return jdbcTemplate.queryForObject(sql, (rs, n)-> new Person(rs), id);
    }
    
}
