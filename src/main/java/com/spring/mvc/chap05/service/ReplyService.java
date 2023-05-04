package com.spring.mvc.chap05.service;


import com.spring.mvc.chap05.dto.ReplyDetailResponseDTO;
import com.spring.mvc.chap05.dto.ReplyListResponseDTO;
import com.spring.mvc.chap05.dto.ReplyPostRequestDTO;
import com.spring.mvc.chap05.dto.page.Page;
import com.spring.mvc.chap05.dto.page.PageMaker;
import com.spring.mvc.chap05.entity.Reply;
import com.spring.mvc.chap05.repository.ReplyMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyService {
    private final ReplyMapper replyMapper;

    // 댓글 목록 조회 서비스
    public ReplyListResponseDTO getList(long boardNo, Page page){
        List<ReplyDetailResponseDTO> replies = replyMapper.findAll(boardNo, page).stream()
                .map(reply -> new ReplyDetailResponseDTO(reply))
                .collect(Collectors.toList());

        int count = replyMapper.count(boardNo);

        return ReplyListResponseDTO.builder()
                .count(count)
                .pageInfo(new PageMaker(page,count))
                .replies(replies)
                .build();
    }

    // 댓글 등록 서비스
    public ReplyListResponseDTO register(final ReplyPostRequestDTO dto) throws SQLException
    {
        log.debug("register service execute!!");
        //dto를 entity로 변환
        Reply reply = dto.toEntity();
        boolean flag = replyMapper.save(reply);

        //예외 처리
        if(!flag) {
            log.warn("reply register rejected!");
            throw new SQLException("댓글 저장 실패!");
        }
        return getList(dto.getBno(), new Page(1,10));
    }

}
