package com.cloud.sample.boardservice.post.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.cloud.sample.boardservice.post.model.Post;

@Component
public class PostRowMapper implements RowMapper<Post>{
    @Override
    public Post mapRow(ResultSet rs, int rowNum) throws SQLException{
        Post post = new Post();
        post.setId(rs.getInt("id"));
        post.setUserId(rs.getString("userId"));
        post.setTitle(rs.getString("title"));
        post.setCtt(rs.getString("ctt"));
        post.setFilename(rs.getString("filename"));
        post.setRegDate(rs.getString("regDate"));
        post.setUseYn(rs.getString("useYn"));
        post.setUrl(rs.getString("url"));
        post.setSize(rs.getLong("size"));
        return post;
    }
}
