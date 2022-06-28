package com.cloud.sample.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.authorization.AuthorizationContext;

@EnableWebFluxSecurity  // spring security 설정 활성화
public class WebFluxSecurityConfig {
    private final static String[] PERMITALL_ANTPATTERNS = {
        ReactiveAuthorization.AUTHORIZATION_URI, "/", "/csrf", "/member/login", "/?*/actuator/?*", "/actuator/?*", "**/configuration/*", "/swagger*/**"
    };  // 인증없이 호출 가능한 url패턴

    private final static String USER_JOIN_ANTPATTERNS = "/member/api/join"; // 가입 url

    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http, ReactiveAuthorizationManager<AuthorizationContext> check) throws Exception {
        http
            .csrf().disable()
            .headers().frameOptions().disable()
        .and()
            .formLogin().disable()
            .httpBasic().authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.UNAUTHORIZED))  // ??
        .and()
            .authorizeExchange()    // 이게 먼지 모르겠네..
            .pathMatchers(PERMITALL_ANTPATTERNS).permitAll()    // 위 배열에 포함되는 패턴은 접속 가능
            .pathMatchers(HttpMethod.POST, USER_JOIN_ANTPATTERNS).permitAll()   // 회원가입 url은 그냥 접속 가능
            .anyExchange().access(check);   // 그외는 무조건 token 검증

        return http.build();
    }
}
