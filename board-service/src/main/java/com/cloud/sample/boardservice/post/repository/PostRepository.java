package com.cloud.sample.boardservice.post.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.cloud.sample.boardservice.post.model.Post;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class PostRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;    // spring jdbc

    public PostRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    // insert
    public Post insert(Post post, String userId){
        KeyHolder keyHolder = new GeneratedKeyHolder(); // id 자동 생성
        SqlParameterSource parameterSource = new MapSqlParameterSource("userId", userId)
        .addValue("title", post.getTitle())
        .addValue("ctt", post.getCtt())
        .addValue("filename", post.getFilename())
        .addValue("useYn", post.getUseYn())
        .addValue("url", post.getUrl())
        .addValue("size", post.getSize());
        
        namedParameterJdbcTemplate.update(PostSql.INSERT, parameterSource, keyHolder);
        post.setId(keyHolder.getKey().intValue());
        return post;
    }
}
