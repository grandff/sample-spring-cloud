package com.cloud.sample.boardservice.post.api;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.sample.boardservice.post.model.Post;
import com.cloud.sample.boardservice.post.service.PostService;

@RestController
public class PostController {
    private PostService postService;
    public PostController(PostService postService){
        this.postService = postService;        
    }

    @PostMapping(value="/api/post/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public Post postInsert(@RequestBody Post post){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("userId :: " + userId);
        return postService.insert(post, userId);
    }
}
