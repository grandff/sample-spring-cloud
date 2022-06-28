package com.cloud.sample.boardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.cloud.sample.boardservice"})
@EntityScan({"com.cloud.sample.boardservice.domain"})
@EnableJpaRepositories(basePackages = {"com.cloud.sample.boardservice"})
@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients(basePackages = {"com.cloud.sample.service.sampleservice.client"})
public class BoardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardServiceApplication.class, args);
	}

}
