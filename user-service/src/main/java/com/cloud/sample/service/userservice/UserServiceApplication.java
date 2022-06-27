package com.cloud.sample.service.userservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@ComponentScan(basePackages = {"com.cloud.sample.service.userservice"})
@EntityScan({"com.cloud.sample.service.userservice.domain"})
@EnableJpaRepositories(basePackages = {"com.cloud.sample.service.userservice"})
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients(basePackages = {"com.cloud.sample.service.userservice.client"})
public class UserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	/*
	 * Rest Template 방법
	 */
	// rest template을 위한 bean 등록
	@Bean
	@LoadBalanced	// url 직접 호출이 아닌 discovery 서버를 통해 연결
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
