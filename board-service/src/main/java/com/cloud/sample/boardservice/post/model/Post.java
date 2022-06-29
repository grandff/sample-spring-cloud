package com.cloud.sample.boardservice.post.model;

import lombok.Data;

@Data
public class Post {
    private Integer id;
    private String userId;
    private String title;
    private String ctt;
    private String filename;
    private String regDate;
    private String useYn;
    private String url;
    private Long size;
}
