package com.cloud.sample.service.sampleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class SampleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleServiceApplication.class, args);
	}

	// 실행 여부 확인
	@GetMapping("/info")
	public String info(@Value("${server.port}") String port) {
		return "User service 동작 포트 : " + port;
	}
	
	// config 설정 확인
	@GetMapping("/config")
	public String configTest(@Value("${spring.datasource.url}") String messageOwner, @Value("${spring.datasource.driver}") String messageContent) {
		return "Configuration File's Message Owner : " + messageOwner + "\n" + "Configuration File's Message Content : " + messageContent;
	}
	
	@GetMapping("/config/database")
	public String database(@Value("${spring.datasource.driver}") String driver,
			@Value("${spring.datasource.url}") String url,
			@Value("${spring.datasource.username}") String username,
			@Value("${spring.datasource.password}") String password,
			@Value("${jwt.token.key}") String tokenKey
			) {
		return "driver : " + driver + "\n" + "url:" + url + "\n" + "username:" + username + "\n" + "password:" + password + "\n\n" + "token key : " + tokenKey;
	}
}
