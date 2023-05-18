package com.spring.mvc.chap05.service;


import com.spring.mvc.chap05.dto.*;
import com.spring.mvc.chap05.dto.page.Page;
import com.spring.mvc.chap05.dto.page.PageMaker;
import com.spring.mvc.chap05.entity.Reply;
import com.spring.mvc.chap05.repository.ReplyMapper;
import com.spring.mvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyService {

    private final ReplyMapper replyMapper;

    // 댓글 목록 조회 서비스
    public ReplyListResponseDTO getList(long boardNo, Page page) {
        List<ReplyDetailResponseDTO> replies = replyMapper.findAll(boardNo, page).stream()
                .map(reply -> new ReplyDetailResponseDTO(reply))
                .collect(Collectors.toList());

        int count = replyMapper.count(boardNo);

        return ReplyListResponseDTO.builder()
                .count(count)
                .pageInfo(new PageMaker(page, count))
                .replies(replies)
                .build();
    }

    // 댓글 등록 서비스
    public ReplyListResponseDTO register(final ReplyPostRequestDTO dto, HttpSession session) throws SQLException {
        log.debug("register service execute!!");
        //dto를 entity로 변환
        Reply reply = dto.toEntity();
        // 세션에서 댓글 작성자 데이터 가져오기
        LoginUserResponseDTO member = (LoginUserResponseDTO) session.getAttribute(LoginUtil.LOGIN_KEY);
        reply.setAccount(member.getAccount());
        reply.setReplyWriter(member.getNickName());
        boolean flag = replyMapper.save(reply);

        //예외 처리
        if (!flag) {
            log.warn("reply register rejected!");
            throw new SQLException("댓글 저장 실패!");
        }


        return getList(dto.getBno(), new Page(1, 10));
    }

    // 댓글 삭제 서비스
    @Transactional //트랜잭션 처리
    public ReplyListResponseDTO delete(final long replyNo) throws Exception {

        long boardNo = replyMapper.findOne(replyNo).getBoardNo();
        replyMapper.deleteOne(replyNo);

        return getList(
                boardNo
                , new Page(1, 10)
        );
    }

    // 댓글 수정 서비스
    public ReplyListResponseDTO modify(final ReplyModifyRequestDTO dto) throws Exception{

        replyMapper.modify(dto.toEntity());
        return getList(
                dto.getBno(),
                new Page(1,10)
        );
    }
}
