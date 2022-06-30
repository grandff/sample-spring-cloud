package com.cloud.sample.boardservice.post.service;

import java.util.List;

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

    // 게시글 상세조회
    public List<Post> view(Long id){
        return this.postRepository.detail(id);
    }

    // 게시글 목록
    public List<Post> list(){
        return this.postRepository.list();
    }
}
