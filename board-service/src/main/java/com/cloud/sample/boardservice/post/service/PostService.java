package com.cloud.sample.boardservice.post.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cloud.sample.boardservice.common.CommonMessageException;
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

    // 게시글 수정
    public Integer update(Post post, String userId){
        findPostByCreatedBy(post.getId(), userId);
        return this.postRepository.update(post, userId);
    }

    // 게시글 번호, 작성자로 게시물 조회
    // 로그인 사용자가 작성자인지 확인
    private void findPostByCreatedBy(Integer id, String userId) throws CommonMessageException{
        if(userId == null || "".equals(userId)){
            throw new CommonMessageException("로그인 후 이용해주세요.");
        }

        List<Post> post = this.postRepository.detail(Long.valueOf(id)); // integer to long
        Post entity = post.get(0);

        if(!userId.equals(entity.getUserId())){
            throw new CommonMessageException("권한이 없습니다.");
        }          
    }

}
