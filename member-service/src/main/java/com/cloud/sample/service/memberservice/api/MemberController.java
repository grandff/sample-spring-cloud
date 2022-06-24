package com.cloud.sample.service.memberservice.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.cloud.sample.service.memberservice.service.MemberService;
import com.cloud.sample.service.memberservice.api.dto.MemberResponseDto;
import com.cloud.sample.service.memberservice.api.dto.MemberJoinRequestDto;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemberController{

    private final MemberService memberService;
    
    // 서비스상태확인(GET)
    @GetMapping("/actuator/helath-info")
    public String getStatus(){
        return String.format("Get Member Service on");
    }    

    // 서비스상태확인(POST)
    @PostMapping("/actuator/helath-info")
    public String postStatus(){
        return String.format("Post Member Service on");
    }

    // 사용자 아이디로 조회(한건)
    @GetMapping("/api/detail/{userId}")
    public MemberResponseDto findByUserId(@PathVariable String userId){
        return memberService.findByUserId(userId);
    }

    // 아이디 또는 이메일 중복 확인
    @PostMapping("/api/exists")
    public Boolean existsUser(@RequestBody MemberJoinRequestDto requestDto){
        return memberService.existsUser(requestDto.getEmail(), requestDto.getUserId());
    }

    // 사용자 회원 가입
    @PostMapping("/api/join")    
    @ResponseStatus(HttpStatus.CREATED)
    public Boolean join(@RequestBody @Valid MemberJoinRequestDto requestDto){
        return memberService.join(requestDto);
    }
}