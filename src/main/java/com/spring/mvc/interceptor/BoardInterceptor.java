package com.spring.mvc.interceptor;


import com.spring.mvc.chap05.entity.Board;
import com.spring.mvc.chap05.repository.BoardMapper;
import com.spring.mvc.util.LoginUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.spring.mvc.util.LoginUtil.isAdmin;
import static com.spring.mvc.util.LoginUtil.isMine;


// 인터셉터: 하위 controller에 요청이 들어가기 전, 후에
//          공통으로 검사할 일들을 정의해놓은 클래스
// 게시판 관련 인가 처리
@Configuration
@Slf4j
@RequiredArgsConstructor
public class BoardInterceptor implements HandlerInterceptor {

    private final BoardMapper boardMapper; //게시글 정보 취득 위함
    @Override
    public boolean preHandle(
            HttpServletRequest request
            , HttpServletResponse response
            , Object handler) throws Exception {

        // 로그인을 했는지 먼저 확인
        // 로그인 안 했으면 로그인페이지로 강제로 리다이렉트
        HttpSession session = request.getSession();


        if (!LoginUtil.isLogin(session)) {
            log.info("this request( {} ) denied!", request.getRequestURI());
            response.sendRedirect("/members/sign-in");
            return false;
        }


        // 삭제요청을 한다면 본인이 쓴 글인지 체크

        /*
         *   1. 우선 로그인계정과 삭제하려는 게시물의 계정명이 일치해야함
         *   2. 로그인계정은 세션에서 구할 수 있음
         *   3. 삭제하려는 게시물의 계정은 어떻게 구할까?
         *       -> 삭제 요청에는 글번호 정보가 있다!
         *       -> 글번호를 db에 조회해서 계정명을 얻어낸다
         * */
        // 삭제요청인지 확인
        String uri = request.getRequestURI();
        if (uri.contains("delete")) {

            //쿼리 파라미터 읽기
            int bno = Integer.parseInt(request.getParameter("bno"));

            // 게시글 정보 읽기
            Board board = boardMapper.findOne(bno);

            String targetAccount = board.getAccount();

            if (!isAdmin(session) || !isMine(session, targetAccount)) {
                response.sendRedirect("/access-deny");
                return false;
            }

        }


        log.info("board interceptor pass!");
        return true;
    }
}
