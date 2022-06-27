package com.cloud.sample.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.cloud.sample.orderservice"})
@EntityScan({"com.cloud.sample.service.orderservice.domain"})
//@EnableJpaRepositories(basePackages = {"com.cloud.sample.service.sampleservice"})
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.cloud.sample.service.orderservice.client"})
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

}
