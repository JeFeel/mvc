package com.spring.mvc.chap04.controller;


import com.spring.mvc.chap04.dto.ScoreRequestDTO;
import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap04.repository.ScoreRepository;
import com.spring.mvc.chap04.repository.ScoreRepositoryImpl;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


/*
    #요청 URL
    1. 학생 성적정보 등록화며니을 보여주고 성적정보 목록조회 처리
    - /score/list : GET

    2. 성적 정보 등록 처리 요청
    - /score/register : POST

    3. 성적정보 삭제 요청
    - /score/remove : POST

    4. 성적정보 상세조회
    -  /score/detail : GET

    5. 성적정보 수정
    - /score/???    : GET
 */

@Controller
@RequestMapping("/score")
//@AllArgsConstructor :  모든 필드 초기화하는 생성자
@RequiredArgsConstructor //final 필드만 초기화하는 생성자
public class ScoreController {

    //저장소에 의존해야 데이터를 받아서 클라이언트에게 응답할 수 있음
    //의존성 주입 받아야 함
    private final ScoreRepository repository;

    //만약에 클래스의 생성자가 단 1개라면
    // 자동으로 @Autowired 써줌

//    @Autowired
//    public ScoreController(ScoreRepository repository) {
//        this.repository = repository;
//    }

    // 1. 성적등록화면 띄우기 + 정보목록조회
    @GetMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "num") String sort) {
        System.out.println("/score/list : GET!");
        System.out.println("정렬 요구사항: "+sort);

        List<Score> scoreList = repository.findAll(sort);
        model.addAttribute("sList", scoreList);

        return "chap04/score-list";
    }

    // 2. 성적 정보 등록 처리 요청
    @PostMapping("/register")
    public String register(ScoreRequestDTO dto) {

        // 입력데이터 (쿼리스트링) 읽기

        System.out.println("/score/register : POST! - "+dto);

        // dto(ScoreDTO)를 entity(Score)로 변환해야 함
        // controller에서 직접 뽑을 수도 있지만
        // 그러면 여기서 setter로 일일이 뽑아야 하므로 객체지향적이지 않음
        Score score = new Score(dto);

        //save 명령
        repository.save(score);

        /*
        * 등록요청해서 JSP 뷰 포워딩을 하면
        * 갱신된 목록을 다시한번 저장소에서 불러와
        * 모델에 담는 추가적인 코드가 필요하지만
        *
        * 리다이렉트를 통해서 위에서 만든 /score/list : GET을
        * 자동으로 다시 보낼 수 있다면 번거로운 코드가
        * 줄어들 수 있겠다
        * */

        //save가 끝나면 /score/list로 자동으로 요청 보내게끔 redirect

        return "redirect:/score/list"; //redirect:요청 URL
    }

    // 3. 성적정보 삭제 요청
    @GetMapping("/remove")
    public String remove(@RequestParam int stuNum) {
        System.out.println("/score/remove : GET!");

        repository.deleteByStuNum(stuNum);
        return "redirect:/score/list";
    }

    // 4. 성적정보 상세조회
    @GetMapping("/detail")
    public String detail(@RequestParam int stuNum, Model model) {

        Score s = repository.findByStuNum(stuNum);
        model.addAttribute("s", s);

        System.out.println("/score/detail : GET!");
        return "chap04/score-detail";
    }

    // 5. 성적정보 수정
    @GetMapping("/modify")
    public String modify(@RequestParam int stuNum, Model model){
        Score s = repository.findByStuNum(stuNum);
        model.addAttribute("s", s);
        return "chap04/score-modify";
    }

    // 6. 수정 완료 처리
    @PostMapping("/modify")
    public String modify(@RequestParam int stuNum,ScoreRequestDTO dto, Model model){
        Score score = repository.findByStuNum(stuNum);
        score.changeScore(dto);
        model.addAttribute("s", score);
        return "redirect:/score/detail?stuNum="+stuNum; //상세보기 페이지로 리다이렉트
    }
}
