package com.sample.cloud.common.commonmodule.common.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter{
    private final String TOKEN_SECRET;
    final String TOKEN_CLAIM_NAME = "authorities";

    public AuthenticationFilter(AuthenticationManager authenticationManager, String tokenSecret){
        super.setAuthenticationManager(authenticationManager);
        this.TOKEN_SECRET = tokenSecret;
    }

    // AuthenticationFilter.doFilter 메소드에서 UsernamePasswordAuthenticationToken 정보를 셋팅할 때 호출됨
    public Claims getClaimsFromToken(String token){
        System.out.println("board service getClaimsFromToken called !! " + token);
        System.out.println("secretkey is " + TOKEN_SECRET);
        return Jwts.parser()
                .setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    // 로그인요청 뿐만 아니라 모든 요청시 호출
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException{
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);    // 토큰 확인?

        if(token== null || "undefined".equals(token) || "".equals(token)){  // 토큰 정보가 없을 경우 처리
            super.doFilter(request, response, chain);
        }else{
            Claims claims = getClaimsFromToken(token);
            String authorities = claims.get(TOKEN_CLAIM_NAME, String.class);    // 권한 확인
            List<SimpleGrantedAuthority> roleList = new ArrayList<>();
            roleList.add(new SimpleGrantedAuthority(authorities));

            String username = claims.getSubject();
            if(username != null){
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, null, roleList));
                chain.doFilter(request, response);
            }else{
                SecurityContextHolder.getContext().setAuthentication(null);
                HttpServletResponse httpServletResponse = (HttpServletResponse) response;
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }                
    }
}
