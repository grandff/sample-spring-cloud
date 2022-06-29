package com.cloud.sample.boardservice.post.service;

import org.springframework.stereotype.Service;

import com.cloud.sample.boardservice.post.model.Post;
import com.cloud.sample.boardservice.post.repository.PostRepository;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository){
        this.postRepository = postRepository;
    }

    // 게시물 등록
    public Post insert(Post post, String userId){        
        return this.postRepository.insert(post, userId);
    }
}
