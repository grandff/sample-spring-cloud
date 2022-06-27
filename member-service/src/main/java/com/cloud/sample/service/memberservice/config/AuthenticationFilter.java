package com.cloud.sample.service.memberservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.cloud.sample.service.memberservice.service.MemberService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

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
import com.cloud.sample.service.memberservice.common.CommonException;

import static org.springframework.util.StringUtils.hasLength;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    private final TokenProvider tokenProvider;
    private final MemberService memberService;

    @Autowired
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
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException{
        System.out.println("successfulAuthentication called!!");
        // 토큰 생성
        tokenProvider.createTokenAndAddHeader(request, response, chain, authResult);
        // 로그인 성공 후처리
        memberService.loginCallback(authResult.getName(), true, "");
    }

    // 로그인 실패 시 호출
    @Transactional
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException{
        System.out.println("unsuccessfulAuthentication called!!");
        String failContent = failed.getMessage();
        if(failed instanceof InternalAuthenticationServiceException){
            System.out.println("해당 사용자는 없습니다.");
        }else if(failed instanceof BadCredentialsException){
            failContent = "패스워드 인증에 실패했습니다. " + failContent;
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        // 로그인 실패 후처리
        String userId = (String) request.getAttribute("userId");
        memberService.loginCallback(userId, false, failContent);
        super.unsuccessfulAuthentication(request, response, failed);
    }

    // 모든 요청시마다 호출
    // 토큰에 담긴 정보로 Authentication 정보 설정
    // 이처리를 하지 않으면 AnonymouseAuthenticationToken 으로 처리 됨
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain){
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String token = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);    // 토큰 확인?
            if(!hasLength(token) || "undefined".equals(token)){
                super.doFilter(request, response, chain);
            }else{
                // 토큰 유형 검사는 API Gateway ReactiveAuthorization 클래스에서 미리 처리
                Claims claims = tokenProvider.getClaimsFromToken(token);

                String username = claims.getSubject();

                if(username == null){
                    // refresh token에는 subject, authorities 정보가 없음
                    SecurityContextHolder.getContext().setAuthentication(null);
                }else{
                    // 어차피 권한은 All 하나라서 그대로 써도 될듯..?
                    List<SimpleGrantedAuthority> roleList = Arrays.stream(claims.get(tokenProvider.TOKEN_CLAIM_NAME, String.class).split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, roleList));
                }

                chain.doFilter(request, response);
            }

        }catch(CommonException e){
            SecurityContextHolder.getContext().setAuthentication(null);
        }catch(ServletException | IOException e){
            SecurityContextHolder.getContext().setAuthentication(null);
        }
    }
}