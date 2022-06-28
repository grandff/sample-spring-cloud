package com.cloud.sample.boardservice.domain.posts;

import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
public class Posts {
    // 게시물복합키
    @EmbeddedId
    private PostsId postsId;

    // 게시판엔티티
    @MapsId("boardNo")  // PostsId.boardNo 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_no")
    private Board board;

    // 게시물제목
    // 게시물내용
    // 게시물답변
    // 첨부파일코드
    // 조회수
    // 공지여부
    // 삭제여부
    // 생성자엔티티
    // 댓글 엔티티
}
