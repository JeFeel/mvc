package com.spring.mvc.chap05.controller;

import com.spring.mvc.chap05.dto.BoardListResponseDTO;
import com.spring.mvc.chap05.dto.BoardRewriteRequestDTO;
import com.spring.mvc.chap05.dto.BoardWriteRequestDTO;
import com.spring.mvc.chap05.dto.page.Page;
import com.spring.mvc.chap05.dto.page.PageMaker;
import com.spring.mvc.chap05.dto.page.Search;
import com.spring.mvc.chap05.entity.Board;
import com.spring.mvc.chap05.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
@Slf4j
public class BoardController {

    private final BoardService boardService;

    // 목록 조회 요청
    @GetMapping("/list")
    public String list(Search page, Model model) {
        log.info("/board/list : GET");
        log.info("page: {}", page);
        List<BoardListResponseDTO> responseDTOS
                = boardService.getList(page);

        // 페이징 알고리즘 작동
        PageMaker maker = new PageMaker(page, boardService.getCount(page));

        model.addAttribute("bList", responseDTOS);
        model.addAttribute("maker", maker);
        model.addAttribute("s", page);

        return "chap05/list";
    }

    // 글쓰기 화면 조회 요청
    @GetMapping("/write")
    public String write() {
        System.out.println("/board/write : GET");
        return "chap05/write";
    }

    // 수정하기 화면 조회 요청
    @GetMapping("/rewrite")
    public String rewrite(BoardRewriteRequestDTO dto, @ModelAttribute("s") Search search, Model model) {
        System.out.println("/board/rewrite : GET");
        model.addAttribute("bno", dto.getBoardNo());
        model.addAttribute("title", dto.getTitle());
        System.out.println("dto = " + dto);
        return "chap05/rewrite";
    }

    // 글 수정 요청 처리
    @PostMapping("/rewrite")
    public String rewrite(BoardRewriteRequestDTO dto){
        System.out.println("/board/rewrite : POST");
        boardService.modify(dto);
        return "redirect:/board/list";
    }
    // 글 등록 요청 처리
    @PostMapping("/write")
    public String write(BoardWriteRequestDTO dto) {
        System.out.println("/board/write : POST");
        boardService.register(dto);
        return "redirect:/board/list";
    }

    // 글 삭제 요청 처리
    @GetMapping("/delete")
    public String delete(int bno) {
        System.out.println("/board/delete : GET");
        boardService.delete(bno);
        return "redirect:/board/list";
    }

    // 글 상세 조회 요청
    @GetMapping("/detail")
    public String detail(int bno, @ModelAttribute("s") Search search, Model model) {
        System.out.println("/board/detail : GET");
        model.addAttribute("b", boardService.getDetail(bno));
        System.out.println("boardService = " + boardService.getDetail(bno));
//        model.addAttribute("s", search);
        return "chap05/detail";
    }

}