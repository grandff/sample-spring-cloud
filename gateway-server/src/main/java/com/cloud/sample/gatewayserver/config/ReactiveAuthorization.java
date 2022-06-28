package com.cloud.sample.gatewayserver.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class ReactiveAuthorization implements ReactiveAuthorizationManager<AuthorizationContext>{
    // api gateway server host
    @Value("${apigateway.host:http://localhost:8000}")
    private String APIGATEWAY_HOST;

    // secret token key    
    @Value("${token.secret}")
    private String TOKEN_SECRET;

    public static final String AUTHORIZATION_URI = "/member/api/authorization/check"; // 인증 URI 
    public static final String REFRESH_TOKEN_URI = "/member/api/token/refresh"; // 토큰재발급 URI

    String authorizationHeader = "";

    // 요청에 대한 사용자 권한여부를 체크해서 true, false를 리턴
    // 헤더에 토큰이 있으면 유효성 체크
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> Authentication, AuthorizationContext context){
        System.out.println("--------check 요청 시작 ---------------");
        ServerHttpRequest request = context.getExchange().getRequest(); // 요청 context
        RequestPath requestPath = request.getPath();    // 요청 path 확인
        HttpMethod httpMethod = request.getMethod();    // 요청 method 확인

        String baseUrl = APIGATEWAY_HOST + AUTHORIZATION_URI + "?httpMethod=" + httpMethod + "&requestPath=" + requestPath; // 인증 full url
        System.out.println("baseUrl : " + baseUrl);

        // 토큰에 저장되어있는 authorization 추출 ?? 대체 httpheaders의 authorizaiton 이 먼디요..
        List<String> authorizations = request.getHeaders().containsKey(org.springframework.http.HttpHeaders.AUTHORIZATION) ? request.getHeaders().get(org.springframework.http.HttpHeaders.AUTHORIZATION) : null;

        // 아무튼 헤더안에 데이터가 있을 경우에만 확인
        if(authorizations != null && authorizations.size() > 0 && StringUtils.hasLength(authorizations.get(0)) && !"undefined".equals(authorizations.get(0))){
            try{
                authorizationHeader = authorizations.get(0);    // ???
                String jwt = authorizationHeader.replace("Bearer", ""); // ???
                String subject = Jwts.parser().setSigningKey(TOKEN_SECRET)
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();  // ???
                
                // 요청 path가 refresh token인 경우 토큰 검증만 하고 인가처리
                if(REFRESH_TOKEN_URI.equals(requestPath + "")){
                    return Mono.just(new AuthorizationDecision(true));
                }

                // 토큰 검증 실패
                if(subject == null || subject.isEmpty())    {
                    System.out.println("토큰 인증 오류");
                    throw new AuthorizationServiceException("토큰 인증 오류");
                }
            
            }catch(IllegalArgumentException e){
                System.out.println("토큰 헤더 오류 : " + e.getMessage());
                throw new AuthorizationServiceException("토큰 헤더 오류");
            }catch(ExpiredJwtException e){
                System.out.println("토큰 유효기간 만료 : " + e.getMessage());
                throw new AuthorizationServiceException("토큰 유효기간 만료");
            }catch(Exception e){
                System.out.println("토큰 인증 오류 exception : " + e.getMessage());
                throw new AuthorizationServiceException("토큰 인증 오류");
            }
        }

        /* !!! 별도 권한에 따라 접근 제어를 하지 않을것이므로 일단 여긴 주석처리.. 토큰 유효성만 체크 중!!!!!
        boolean granted = false;
        try{
            String token = authorizationHeader; // 위에서 추출한 토큰값....???
            Mono<Boolean> body = WebClient.create(baseUrl)
                .get()
                .headers(httpHeaders -> {
                    httpHeaders.add(org.springframework.http.HttpHeaders.AUTHORIZATION, token);
                })
                .retrieve().bodyToMono(Boolean.class);  // ??? 뭔가 권한확인 url 쪽으로 요청을 보내는거 같은데...

            granted = body.blockOptional().orElse(false);   // 권한확인 ??
            
            System.out.println("granted :: " + granted);

        }catch(Exception e){
            System.out.println("인가오류 :: " + e.getMessage());
            throw new AuthorizationServiceException("인가 요청 오류 발생");
        }
        System.out.println("--------check 요청 종료 ---------------");
        return Mono.just(new AuthorizationDecision(granted));
        */

        return Mono.just(new AuthorizationDecision(true));
    }
}
