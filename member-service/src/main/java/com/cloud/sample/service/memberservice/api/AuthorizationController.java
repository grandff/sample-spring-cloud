package com.cloud.sample.service.memberservice.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.sample.service.memberservice.service.AuthorizationService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthorizationController {
    // 인가 서비스
    private final AuthorizationService authorizationService;
    
    // 인가여부 확인(gateway에서 호출함)
    @GetMapping(value = "/api/authorization/check")
    public Boolean isAuthorization(@RequestParam("httpMethod") String httpMethod, @RequestParam("requestPath") String requestPath){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String userId = authentication.getName();   // 이게.. 아이디야 토큰이야?
        List<String> roles = authentication.getAuthorities().stream().map(GrantedAuthority::toString).collect(Collectors.toList()); // 권한
        System.out.println("userId :: " + userId);

        // 권한으로 조회하기
        Boolean isAuth = authorizationService.isAuthorization(roles, httpMethod, requestPath);

        return isAuth;
    }
}
