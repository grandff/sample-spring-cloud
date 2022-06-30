package com.cloud.sample.boardservice.post.repository;

public class PostSql{
    // 등록
    public static final String INSERT = "INSERT INTO POSTS (userId, title, ctt, filename, regDate, url, size) VALUES (:userId, :title, :ctt, :filename, NOW(), :url, :size)   ";
    // 조회
    public static final String SELECT = "SELECT id, userId, title, ctt, filename, regDate, useYn, url, size FROM POSTS ";
    // 아이디 조건
    public static final String ID_CONDITION = " WHERE id = :id";
    // 수정
    public static final String UPDATE = "UPDATE title = :title, ctt = :ctt, regDate = NOW() FROM POSTS WHERE id = :id";
}