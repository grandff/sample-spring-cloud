package com.cloud.sample.service.memberservice.config;

import static org.springframework.util.StringUtils.hasLength;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.sample.service.memberservice.api.dto.MemberLoginRequestDto;
import com.cloud.sample.service.memberservice.common.CommonException;
import com.cloud.sample.service.memberservice.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    
    private final TokenProvider tokenProvider;
    private final MemberService memberService;

    @Autowired
    public AuthenticationFilter(AuthenticationManager authenticationManager, TokenProvider tokenProvider, MemberService memberService) {
        super.setAuthenticationManager(authenticationManager);
        this.tokenProvider = tokenProvider;
        this.memberService = memberService;
    }

    // ????????? ?????? ??? ?????? ?????? ?????????. ?????? ????????? ?????? ??????????????? ??????.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("----------?????????????????????------------");
        try{
            // post method?????? input stream?????? ???????????? ???...
            MemberLoginRequestDto loginData = new ObjectMapper().readValue(request.getInputStream(), MemberLoginRequestDto.class);
            // ?????? ?????? ???
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginData.getUserId(), loginData.getPassword(), new ArrayList<>());
            // ???????????? ??????
            return getAuthenticationManager().authenticate(authToken);            
        }catch(IOException e){
            System.out.println("????????? ?????? : " + e.getLocalizedMessage());
            throw new RuntimeException(e);
        }finally{
            System.out.println("----------?????????????????????------------");
        }        
    }

    // ????????? ?????? ?????? ??? ??????
    @Transactional
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException{
        System.out.println("successfulAuthentication called!!");
        // ?????? ??????
        tokenProvider.createTokenAndAddHeader(request, response, chain, authResult);
        // ????????? ?????? ?????????
        memberService.loginCallback(authResult.getName(), true, "");
    }

    // ????????? ?????? ??? ??????
    @Transactional
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException{
        System.out.println("unsuccessfulAuthentication called!!");
        String failContent = failed.getMessage();
        if(failed instanceof InternalAuthenticationServiceException){
            System.out.println("?????? ???????????? ????????????.");
        }else if(failed instanceof BadCredentialsException){
            failContent = "???????????? ????????? ??????????????????. " + failContent;
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        // ????????? ?????? ?????????
        String userId = (String) request.getAttribute("userId");
        memberService.loginCallback(userId, false, failContent);
        super.unsuccessfulAuthentication(request, response, failed);
    }

    // ?????? ??????????????? ??????
    // ????????? ?????? ????????? Authentication ?????? ??????
    // ???????????? ?????? ????????? AnonymouseAuthenticationToken ?????? ?????? ???
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain){
        try {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String token = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);    // ?????? ???????
            if(!hasLength(token) || "undefined".equals(token)){
                super.doFilter(request, response, chain);
            }else{
                // ?????? ?????? ????????? API Gateway ReactiveAuthorization ??????????????? ?????? ??????
                Claims claims = tokenProvider.getClaimsFromToken(token);

                String username = claims.getSubject();

                if(username == null){
                    // refresh token?????? subject, authorities ????????? ??????
                    SecurityContextHolder.getContext().setAuthentication(null);
                }else{
                    // ????????? ????????? All ???????????? ????????? ?????? ??????..?
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