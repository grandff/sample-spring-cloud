package com.cloud.sample.boardservice.post.repository;

import java.util.List;

import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
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
    private final PostRowMapper rowMapper;

    public PostRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate, PostRowMapper rowMapper){
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.rowMapper = rowMapper;
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
        post.setUserId(userId);
        return post;
    }

    // detail select
    public List<Post> detail(Long id){  // query는 list로 리턴해줌
        String query = PostSql.SELECT + PostSql.ID_CONDITION;
        SqlParameterSource param = new MapSqlParameterSource("id", id);        
        return namedParameterJdbcTemplate.query(query, param, this.rowMapper);
    }

    // total list
    public List<Post> list(){
        return namedParameterJdbcTemplate.query(PostSql.SELECT, EmptySqlParameterSource.INSTANCE, this.rowMapper);
    }

    // update
    public Integer update(Post post, String userId){           
        String query = PostSql.UPDATE;
        SqlParameterSource param = new MapSqlParameterSource("id", post.getId())
        .addValue("title", post.getTitle())
        .addValue("ctt", post.getCtt());
        return namedParameterJdbcTemplate.update(query, param);
    }
}
