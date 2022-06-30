package com.cloud.sample.boardservice.post.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    
    // 서비스상태확인(GET)
    @GetMapping("/actuator/helath-info")
    public String getStatus(){
        return String.format("Get Board Service on");
    }    

    // 서비스상태확인(POST)
    @PostMapping("/actuator/helath-info")
    public String postStatus(){
        return String.format("Post Board Service on");
    }

    // 게시글 등록
    @PostMapping(value="/api/post/insert")
    @ResponseStatus(HttpStatus.CREATED)
    public Post postInsert(@RequestBody Post post){
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        return postService.insert(post, userId);
    }

    // 게시글 단건 조회
    @GetMapping(value="/api/post/view/{postId}")
    public List<Post> postView(@PathVariable Long postId){
        return postService.view(postId);
    }

    // 게시글 전체 조회
    @GetMapping(value="/api/post/list")
    public List<Post> postList(){
        return postService.list();
    }

    // 게시글 수정
    @PostMapping(value="/api/post/update")    
    public Integer update(@RequestBody Post post) {
        
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        
        return postService.update(post, userId);
    }
    

    // 게시글 삭제

    // 파일첨부?
}
