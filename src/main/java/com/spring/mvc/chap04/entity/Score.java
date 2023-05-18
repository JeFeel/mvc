package com.spring.mvc.chap04.entity;


import com.spring.mvc.chap04.dto.ScoreRequestDTO;
import lombok.*;

import java.sql.ResultSet;
import java.sql.SQLException;


@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Score {

    private String name;    //학생 이름
    private int kor, eng, math; //국, 영, 수  점수

    //서버가 만들어야되는 데이터 (서버가 계산)
    private int stuNum; //학번
    private int total; //총점\
    private double avg; //평균
    private Grade grade; // 학점

    public Score(ScoreRequestDTO dto) {
        this.name = dto.getName();
        changeScore(dto);
    }

    public Score(ResultSet rs) throws SQLException {
        this.stuNum = rs.getInt("stu_num");
        this.name = rs.getString("name"); //DB의 컬럼이랑 맞출것!
        this.kor = rs.getInt("kor");
        this.eng = rs.getInt("eng");
        this.math = rs.getInt("math");
        this.total = rs.getInt("total");
        this.avg = rs.getDouble("avg");
        this.grade = Grade.valueOf(rs.getString("grade"));
    }

    public void changeScore(ScoreRequestDTO dto) { //dto를 전달받는 생성자
        this.kor = dto.getKor();
        this.eng = dto.getEng();
        this.math = dto.getMath();
        calcTotalAndAvg(); //총점, 평균 계산 메서드 추출
        calcGrade();
    }

    private void calcGrade() {
        if (avg >= 90) {
            this.grade = Grade.A;
        } else if (avg >= 80) {
            this.grade = Grade.B;
        } else if (avg >= 70) {
            this.grade = Grade.C;
        } else if (avg >= 60) {
            this.grade = Grade.D;
        } else {
            this.grade = Grade.F;
        }
    }

    private void calcTotalAndAvg() {
        this.total = kor + eng + math;
        this.avg = Math.round((total/ 3.0)*100)/100.0;  //소숫점 둘째 자리까지만 표현
    }
}
