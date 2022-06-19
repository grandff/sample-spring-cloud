package org.egovframe.cloud.apigateway.filter;

import java.util.List;
import java.util.Objects;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class CustomAuthFilter extends AbstractGatewayFilterFactory<CustomAuthFilter.Config>{
	public CustomAuthFilter() {
		super(Config.class);
	}
	
	@Override
	public GatewayFilter apply(Config config) {
		// pre filter
		return ((exchange, chain) -> {
			ServerHttpRequest request = exchange.getRequest();		// pre filter. spring reactive interface를 import 해야함!
			
			// request header에 token이 존재하지 않을 때
			if(!request.getHeaders().containsKey("token")) {
				return handleUnAuthorized(exchange);		// 401 error
			}
			
			// request header에서 token 받아오기
			List<String> token = request.getHeaders().get("token");
			String tokenString = Objects.requireNonNull(token).get(0);			
			
			// 토큰 검증
			if(!tokenString.equals("A.B.C")) {
				return handleUnAuthorized(exchange);
			}
			
			return chain.filter(exchange);	// 토큰이 일치할 때
		});
	}
	
	// 토큰 불일치 처리
	private Mono<Void> handleUnAuthorized(ServerWebExchange exchange){
		ServerHttpResponse response  = exchange.getResponse();	// post filter. spring reactive interface를 import 해야함!		
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		return response.setComplete();
	}
	
	public static class Config {
		
	}
}
