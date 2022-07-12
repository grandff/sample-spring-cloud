package com.cloud.sample.boardservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.sample.cloud.commonmodule.common.config.AuthenticationFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화시켜 준다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${token.secret}")
    private String TOKEN_SECRET;

     /**
     * 스프링 시큐리티 설정
     *
     * @param http
     * @throws Exception
     */    

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 사용하기 때문에 세션은 비활성화
            .and()
                .addFilter(getAuthenticationFilter());        
    }

    // authentication 정보 설정
    // 이 처리를 안하면 anonymusToken으로 처리됨
    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        return new AuthenticationFilter(authenticationManager(), TOKEN_SECRET);
    }
}