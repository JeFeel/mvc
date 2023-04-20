package com.spring.mvc.chap04.entity;


import com.spring.mvc.chap04.dto.ScoreRequestDTO;
import lombok.*;


@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Score {

    private String name;    //학생 이름
    private int kor, eng, math; //국, 영, 수  점수

    //서버가 만들어야되는 데이터 (서버가 계산)
    private int stuNum; //학번
    private int total; //총점
    private double average; //평균
    private Grade grade; // 학점

    public Score(ScoreRequestDTO dto) {
        this.name = dto.getName();
        changeScore(dto);
    }
    public void changeScore(ScoreRequestDTO dto) { //dto를 전달받는 생성자
        this.kor = dto.getKor();
        this.eng = dto.getEng();
        this.math = dto.getMath();
        calcTotalAndAvg(); //총점, 평균 계산 메서드 추출
        calcGrade();
    }

    private void calcGrade() {
        if(average>=90){
            this.grade= Grade.A;
        }else if(average>=80){
            this.grade=Grade.B;
        }else if(average>=70){
            this.grade=Grade.C;
        }else if(average>=60){
            this.grade=Grade.D;
        }else{
            this.grade=Grade.F;
        }
    }

    private void calcTotalAndAvg() {
        this.total = kor+eng+math;
        this.average = total/ 3.0;
    }
}
