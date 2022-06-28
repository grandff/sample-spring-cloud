package com.cloud.sample.deliveryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

@ComponentScan(basePackages = {"com.cloud.sample.deliveryservice"})
@EntityScan({"com.cloud.sample.deliveryservice.domain"})
//@EnableJpaRepositories(basePackages = {"com.cloud.sample.service.sampleservice"})
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.cloud.sample.deliveryservice.client"})
@RestController
public class DeliveryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryServiceApplication.class, args);
	}

}
