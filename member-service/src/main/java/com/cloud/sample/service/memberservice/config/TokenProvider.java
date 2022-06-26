package com.cloud.sample.service.memberservice.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.stereotype.Component;
import com.cloud.sample.service.memberservice.service.MemberService;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.cloud.sample.service.memberservice.domain.Member;
import com.cloud.sample.service.memberservice.api.dto.MemberResponseDto;

@Component
public class TokenProvider {
    private final MemberService memberService;

    @Value("${token.secret}")
    private String TOKEN_SECRET;    // secret key

    @Value("${token.expiration_time}")
    private String TOKEN_EXPIRATION_TIME;

    @Value("${token.refresh_time}")
    private String TOKEN_REFRESH_TIME;

    final String TOKEN_CLAIM_NAME = "authorities";
    final String TOKEN_ACCESS_KEY = "access-token";
    final String TOKEN_REFRESH_KEY = "refresh-token";
    final String TOKEN_USER_ID = "token-id";

    // 로그인 후 토큰을 생성하고 헤더에 정보를 담음
    public void createTokenAndAddHeader(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult){
        // 로그인 성공 후 토큰 처리
        String userId = authResult.getName();
        String authorities = "All"; // 권한 설정한게 없으니까 service에서 맞춘 권한과 동일하게 처리

        // jwt access 토큰 생성
        String accessToken = createAccessToken(authorities, userId);

        // jwt refresh 토큰 생성 후 사용자 도메인에 저장해서 토큰 재생성 요청시 활용함
        String refreshToken = createRefreshToken();
        memberService.updateRefreshToken(userId, refreshToken);

        // header에 토큰 셋팅
        response.addHeader(TOKEN_ACCESS_KEY, accessToken);
        response.addHeader(TOKEN_REFRESH_KEY, refreshToken);
        response.addHeader(TOKEN_USER_ID, userId);
    }

    // jwt access token 생성
    private String createAccessToken(String authorities, String userId){
        return Jwts.builder()
                .setSubject(userId)
                .claim(TOKEN_CLAIM_NAME, authorities)
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(TOKEN_EXPIRATION_TIME)))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .compact();
    }

    // jwt refresh token 생성
    private String createRefreshToken(){
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(TOKEN_REFRESH_TIME)))
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .compact();
    }

    // 사용자가 있으면 access token을 새로 발급해서 리턴
    public String refreshToken(String refreshToken, HttpServletResponse response){
        // refresh token으로 유효한 사용자 찾기
        Member member = memberService.findByRefreshToken(refreshToken);
        // 있으면 새로 발급
        String accessToken = createAccessToken("All", member.getUserId());
        
        String filteredRefreshToken = refreshToken.replaceAll("\r","").replaceAll("\n",""); // 개행문자 제거??

        // header에 토큰 셋팅
        response.addHeader(TOKEN_ACCESS_KEY, accessToken);
        response.addHeader(TOKEN_REFRESH_KEY, filteredRefreshToken);
        response.addHeader(TOKEN_USER_ID, member.getUserId());
        return accessToken;

    }

    // dofilter에서 호출
    // UsernamePasswordAuthenticationToken 정보를 셋팅할 때 호출됨.. 이라고함...
    public Claims getClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(TOKEN_SECRET)
                .parseClaimsJws(token)
                .getBody();
    }
}