package com.cloud.sample.boardservice.domain.posts;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Embeddable
public class PostsId implements Serializable{
    private static final long serialVersionUID = 2286680623535L;
    
    // 게시판 번호
    @Column(columnDefinition = "int(9)")
    private Integer boardNo;    // @mapsId("boardNo")

    // 게시물 번호
    @Column(columnDefinition = "int(9)")
    private Integer postsNo;

    // 빌드패턴클래스 생성자
    @Builder
    public PostsId(Integer boardNo, Integer postsNo){
        this.boardNo = boardNo;
        this.postsNo = postsNo;
    }

    // override methods
    // hash code
    @Override
    public int hashCode(){
        return Objects.hash(boardNo, postsNo);
    }

    // equals
    @Override
    public boolean equals(Object object){
        if(this == object) return true;
        if(!(object instanceof PostsId)) return false;
        PostsId that = (PostsId) object;
        return Objects.equals(boardNo, that.getBoardNo()) && Objects.equals(postsNo, that.getPostsNo());
    }

    // tostring
    @Override
    public String toString() {
        return this.boardNo + "_" + this.postsNo;
    }
}
