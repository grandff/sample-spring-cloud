package com.cloud.sample.service.memberservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.cloud.sample.service.memberservice.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cloud.sample.service.memberservice.api.dto.MemberLoginRequestDto;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    private final TokenProvider tokenProvider;
    private final MemberService memberService;

    public AuthenticationFilter(AuthenticationManager authenticationManager, TokenProvider tokenProvider, MemberService memberService) {
        super.setAuthenticationManager(authenticationManager);
        this.tokenProvider = tokenProvider;
        this.memberService = memberService;
    }

    // 로그인 요청 시 호출 되는 메서드. 계정 정보를 받아 인증정보를 생성.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("----------로그인요청시작------------");
        try{
            // post method라서 input stream으로 받는다고 함...
            MemberLoginRequestDto loginData = new ObjectMapper().readValue(request.getInputStream(), MemberLoginRequestDto.class);
            // 토큰 생성 ???
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginData.getUserId(), loginData.getPassword(), new ArrayList<>());
            // 인증정보 생성
            return getAuthenticationManager().authenticate(authToken);            
        }catch(IOException e){
            System.out.println("로그인 에러 : " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }finally{
            System.out.println("----------로그인요청종료------------");
        }        
    }

    // 로그인 인증 성공 후 호출
    @Transactional
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chian, Authentication authResult) throws IOException, ServletException{
        // 토큰 생성
        tokenProvider.createTokenAndAddHeader(request, response, chain, authResult);
        // 로그인 성공 후처리
        memberService.loginCallback();
    }

    // 로그인 실패 시 호출

    // 모든 요청시마다 호출
    // 토큰에 담긴 정보로 Authentication 정보 설정
    // 이처리를 하지 않으면 AnonymouseAuthenticationToken 으로 처리 됨

}