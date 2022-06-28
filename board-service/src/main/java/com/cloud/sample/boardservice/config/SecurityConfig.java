package com.cloud.sample.boardservice.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화시켜 준다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

     /**
     * 스프링 시큐리티 설정
     *
     * @param http
     * @throws Exception
     */
    
    final String[] PERMITALL_PATTERNS = {"/**", "/swagger-ui.html"};

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 사용하기 때문에 세션은 비활성화
            .and()
                .authorizeRequests()
                .antMatchers(PERMITALL_PATTERNS).permitAll();
                //.anyRequest().access("@authorizationService.isAuthorization(request, authentication)") // 호출 시 권한 인가 데이터 확인
            //.and()
            //    .addFilter(getAuthenticationFilter())
             //   .logout()
               // .logoutSuccessUrl("/");
    }
}