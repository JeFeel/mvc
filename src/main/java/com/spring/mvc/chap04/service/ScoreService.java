package com.spring.mvc.chap04.service;


import com.spring.mvc.chap04.dto.ScoreListResponseDTO;
import com.spring.mvc.chap04.dto.ScoreRequestDTO;
import com.spring.mvc.chap04.entity.Score;
import com.spring.mvc.chap04.repository.ScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


//controller와 repository 사이에서 비즈니스 로직 처리
//ex) 트랜잭션 처리, 예외처리, dto 변환처리
//@RequiredArgsConstructor
@Service //서비스 bean 등록
public class ScoreService {

//    private final ScoreRepository scoreRepository;

    private final ScoreMapper scoreRepository;

    @Autowired
    public ScoreService(ScoreMapper scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    //목록조회 중간처리
    /*
    *   컨트롤러는 데이터베이스를 통해
    *   성적정보 리스트를 가져오길 원한다.
    *   그런데 데이터베이스는 성적정보를 모두 모아서준다
    *   컨트롤러는 정보를 일부만 받았으면 좋겠다.
    * */
    public List<ScoreListResponseDTO> getList(String sort){


        //scoreList에서 원하는 정보만 추출하고 이름을 마스킹해서
        // 다시 DTO 리스트로 변환해줌
        return scoreRepository.findAll(sort)
                .stream()
                .map(ScoreListResponseDTO::new)
                .collect(Collectors.toList());
    }

    //등록 중간처리
    //controller는 DTO를 줬지만
    //repository는 ScoreEntity를 요청
    //==> service에서 처리
    public boolean insertScore(ScoreRequestDTO dto){
        // dto(ScoreDTO)를 entity(Score)로 변환해야 함
        // controller에서 직접 뽑을 수도 있지만
        // 그러면 여기서 setter로 일일이 뽑아야 하므로 객체지향적이지 않음

        //save 명령
        return scoreRepository.save(new Score(dto));
    }


    //삭제 중간처리
    public boolean delete(int stuNum){
        return scoreRepository.deleteByStuNum(stuNum);
    }

    //상세조회, 수정화면 조회 중간처리
    public Score retrieve(int stuNum){
        //만약에 스코어 전체 말고
        // 몇개만 추리고 전후처리해서 줌
        return scoreRepository.findByStuNum(stuNum);
    }

    // 수정처리
    public void modify(Score score) {
        scoreRepository.modifyByStuNum(score);
    }
}
