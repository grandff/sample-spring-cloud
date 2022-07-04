package com.cloud.sample.service.memberservice.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cloud.sample.service.memberservice.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정들을 활성화시켜 준다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;  // 패스워드 해싱

     /**
     * 스프링 시큐리티 설정
     *
     * @param http
     * @throws Exception
     */
    
    final String[] PERMITALL_PATTERNS = {"/member/**", "/swagger-ui.html"}; // 해당 URL만 접근 가능

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 토큰 사용하기 때문에 세션은 비활성화
            .and()
                .authorizeRequests()    // 시큐리티 처리에 HttpServletRequest 사용 설정
                .antMatchers(PERMITALL_PATTERNS).permitAll()
                //.anyRequest().access("@authorizationService.isAuthorization(request, authentication)") // 호출 시 권한 인가 데이터 확인 이거 대체 어떻게 데이터 불러옴??
            .and()
                .addFilter(getAuthenticationFilter())   // 로그인 처리를 위해 커스텀 필터 등록
                .logout()   // 기본값은 /logout
                .logoutSuccessUrl("/");
    }

    // 로그인 인증 정보를 받아 토큰을 발급할 수 있도록 필터 등록
    // 아마 로그인 시도를 하게 되면 AuthenticifationFilter가 호출이 되는듯함...!
    private AuthenticationFilter getAuthenticationFilter() throws Exception {
        return new AuthenticationFilter(authenticationManager(), tokenProvider, memberService);
    }

    // 인증 관련 로그인처리.. DB에서 조회해서 일치하는지 체크
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // memberService의 loadUserByUserName 메소드 호출
        auth.userDetailsService(memberService).passwordEncoder(bCryptPasswordEncoder);
    }
}