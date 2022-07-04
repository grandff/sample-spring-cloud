package com.cloud.sample.service.memberservice.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.sample.service.memberservice.api.dto.MemberJoinRequestDto;
import com.cloud.sample.service.memberservice.api.dto.MemberResponseDto;
import com.cloud.sample.service.memberservice.config.TokenProvider;
import com.cloud.sample.service.memberservice.service.MemberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController{

    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    
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

    // 토큰 refresh
    @PutMapping("/api/token/refresh")
    public void refreshToken(HttpServletRequest request ,HttpServletResponse response){
        String refreshToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        tokenProvider.refreshToken(refreshToken, response);
    }
}