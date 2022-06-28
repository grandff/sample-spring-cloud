package com.cloud.sample.boardservice.domain.posts;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.cloud.sample.boardservice.domain.board.Board;
import com.cloud.sample.boardservice.domain.member.Member;

import lombok.Builder;
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
    @Column(nullable = false, length = 100)
    private String postsTitle;
    
    // 게시물내용
    @Column(nullable = false, columnDefinition = "longtext")
    private String postsContent;
    
    // 게시물답변
    @Column(columnDefinition = "longtext")
    private String postsAnswerContent;
    
    // 첨부파일코드
    @Column
    private String attachmentCode;
    
    // 조회수
    @Column(columnDefinition = "int(9) default'0'")
    private Integer readCount;
    
    // 삭제여부
    @Column(nullable = false, columnDefinition = "tinyint(1) default'0'")
    private Integer deleteAt;
    
    // 생성자엔티티
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="createdBy", referencedColumnName = "userId", insertable = false, updatable = false)
    private Member creator;
    
    // 빌더 패턴 클래스 생성자
    @Builder
    public Posts(Board board, PostsId postsId, String postsTitle, String postsContent, 
    		String postsAnswerContent, String attachmentCode, Integer readCount, 
    		Integer deleteAt, Member creator) {
    	this.postsId = postsId;
    	this.postsTitle = postsTitle;
    	this.postsContent = postsContent;
    	this.postsAnswerContent = postsAnswerContent;
    	this.attachmentCode = attachmentCode;
    	this.readCount = readCount;
    	this.deleteAt = deleteAt;
    	this.creator = creator;    	
    	setBoard(board);
    }
    
    // 연관관계 설정
    public void setBoard(Board board) {
    	this.board = board;
    	board.getPosts().add(this);
    }
    
    // 게시물 수정
    public Posts update(String postsTitle, String postsContent, String attachmentCode) {
        this.postsTitle = postsTitle;
        this.postsContent = postsContent;
        this.attachmentCode = attachmentCode;

        return this;
    }
    
    // 게시물 삭제여부 수정
    public Posts updateDeleteAt(Integer deleteAt) {
        this.deleteAt = deleteAt;

        return this;
    }
    
    // 조회수 증가
    public Posts updateReadCount() {
        this.readCount += 1;

        return this;
    }
}
