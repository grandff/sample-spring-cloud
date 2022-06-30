package com.cloud.sample.boardservice.post.repository

class PostSql {
    public static final String INSERT = """
        INSERT INTO POSTS (userId, title, ctt, filename, regDate, useYn, url, size) VALUES (:userId, :title, :ctt, :filename, NOW(), :useYn, :url, :size);         
    """
}