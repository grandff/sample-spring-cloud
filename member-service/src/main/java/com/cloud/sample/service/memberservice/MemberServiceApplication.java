package com.cloud.sample.service.memberservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;


@ComponentScan(basePackages = {"com.cloud.sample.service.memberservice"})
@EntityScan({"com.cloud.sample.service.memberservice.domain"})
@EnableJpaRepositories(basePackages = {"com.cloud.sample.service.memberservice"})
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients(basePackages = {"com.cloud.sample.service.memberservice.client"})
public class MemberServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberServiceApplication.class, args);
	}

	// security encoder bean 등록
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
