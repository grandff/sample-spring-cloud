package com.cloud.sample.boardservice.domain.board;

import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Entity;

@Getter
@NoArgsConstructor
@Entity
public class Board{
    // 게시판 번호
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(columnDefinition="int(9)")
    private Integer boardNo;

    // 게시판 제목
    @Column(nullable=false, length=100)
    private String boardName;
    
    // 게시물 표시 수
    @Column(nullable=false, columnDefinition="mediumint(5) default '10'")
    private Integer postDiplayCount;

    // 페이지 표시 수
    // 신규 표시 일 수
    // 에디터 사용 여부
    // 사용자 작성 여부
    // 댓글 사용 여부
    // 업로드 사용 여부
    // 업로드 제한 수
    // 업로드 제한 크기
    // 게시물 엔티티

    // 빌더 패턴
}