package com.cloud.sample.boardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.cloud.sample.boardservice"})
@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients(basePackages = {"com.cloud.sample.service.sampleservice.client"})
public class BoardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardServiceApplication.class, args);
	}

}
