package com.cloud.sample.boardservice.domain.board;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.cloud.sample.boardservice.domain.posts.Posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @Column(nullable = false, columnDefinition = "mediumint(5) default '10'")
    private Integer pageDisplayCount;    
    
    // 업로드 사용 여부
    @Column(nullable = false, columnDefinition = "tinyint(1) default '0'")
    private Boolean uploadUseAt;
    
    // 게시물 엔티티
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    @OnDelete(action=OnDeleteAction.CASCADE)
    private List<Posts> posts = new ArrayList<>();

    // 빌더 패턴
    @Builder
    public Board(Integer boardNo, String boardName, Integer postDisplayCount, Integer pageDisplayCount, Boolean uploadUseAt) {
    	this.boardNo = boardNo;
    	this.boardName = boardName;
    	this.postDiplayCount = postDisplayCount;
    	this.pageDisplayCount = pageDisplayCount;
    	this.uploadUseAt = uploadUseAt;
    }
}