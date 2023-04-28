package com.spring.mvc.chap05.entity;

import com.spring.mvc.chap05.dto.BoardRewriteRequestDTO;
import com.spring.mvc.chap05.dto.BoardWriteRequestDTO;
import lombok.*;

import java.time.LocalDateTime;

@Setter @Getter
@ToString @EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    private int boardNo; // 게시글 번호
    private String title; // 제목
    private String content; // 내용
    private int viewCount; // 조회수
    private LocalDateTime regDateTime; // 작성일자시간

    /*
    * create table tbl_board (
   board_no int(10) auto_increment primary key,
   title VARCHAR(80) not null,
   content VARCHAR(2000),
   view_count int(10) default 0,
   reg_date_time DATETIME default current_timestamp
    );*/

    public Board(int boardNo, String title, String content) {
        this.boardNo = boardNo;
        this.title = title;
        this.content = content;
        this.regDateTime = LocalDateTime.now();
    }

    public Board(BoardWriteRequestDTO dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.regDateTime = LocalDateTime.now();
    }

    public Board(BoardRewriteRequestDTO dto) {
        this.boardNo = dto.getBoardNo();
        this.title = dto.getTitle();
        this.content = dto.getContent();
        this.regDateTime = LocalDateTime.now();
    }
}