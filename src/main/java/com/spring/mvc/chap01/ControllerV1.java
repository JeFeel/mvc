package com.spring.mvc.chap01;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;


//어떤 요청들을 처리할지 공통 URL을 설계
@RequestMapping("/spring/*")
// 이 클래스의 객체를 스프링이 관리하도록 bean을 등록
@Controller //@Component와 같은 개념
public class ControllerV1 {

    // 세부요청들은 메서드를 통해 처리
    @RequestMapping("/hello") // http://localhost:8181/spring/hello
    public String hello() {
        System.out.println("\n=====헬로 요청이 들어옴!=====\n");
        //어떤 JSP 열어줄지 경로를 적음
        return "hello";
    }

    // /spring/food 요청이 오면 food.jsp를 열어보세요
    @RequestMapping("/food")
    public String food() {
        return "chap01/food";
    }

    //==============요청 파라미터 읽기( Query String parameter)======================//
    // == 1. HttpServletRequest 사용하기
    // ==> ex ) /spring/person?name=kim&age=30

    @RequestMapping("/person")
    public String person(HttpServletRequest request) {
        String name = request.getParameter("name");
        String age = request.getParameter("age");
        System.out.println("name = " + name);
        System.out.println("age = " + age);

        return "";
    }

    // == 2. @RequestParam 사용하기
    // ==> ex ) /spring/major?stu=kim&major=business&grade=3
    @RequestMapping("/major")
    public String major(
            String stu,  //url이랑 이름도 같고 defaulValue 설정 안 할거면 @RequestParam 생략 가능!
            @RequestParam("major") String mj,   //url에 괄호안 단어가 있을경우
            @RequestParam(defaultValue = "1") int grade  //전달되지 않은 경우 기본값
    ) {

        //지역변수를 설정했다 가정
        //파라미터를 바꿔야 하는데 url 쪽에는 변경하기 싫다?
        //@RequestParam() 안에 설정해줌
        String major = "전공자";

        System.out.println("stu = " + stu);
        System.out.println("major = " + mj);
        System.out.println("grade = " + grade);

        return "";
    }

    // == 3. 커맨드 객체 이용하기
    // == 쿼리 스트링의 양이 너무 많을 경우 또는 연관성이 있을경우
    // ==> ex) /spring/order?oNum=20230419007-P&goods=구두&amount=3&price=50000....

    @RequestMapping("/order")
    public String order(OrderRequestDTO dto){
        System.out.println("dto = " + dto);
        return "";
    }


    // == 4. URL에 경로로 붙어있는 데이터 읽기
    // ==> /spring/member/hong/107
    //      hong이라는 유저의 107번 게시글 읽기
    @RequestMapping("/member/{userName}/{bNo}")
    public String member(
            @PathVariable String userName, // / 뒤에 있는거 읽음,  @PathVariable은 생략 불가
            @PathVariable long bNo
    ){
        System.out.println("userName = " + userName);
        System.out.println("bNo = " + bNo);

        return "";
    }

}
