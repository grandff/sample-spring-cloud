package com.cloud.sample.service.teamservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.cloud.sample.service.teamservice"})
@EntityScan({"com.cloud.sample.service.teamservice.domain"})
@EnableJpaRepositories(basePackages = {"com.cloud.sample.service.teamservice"})
@SpringBootApplication
@EnableDiscoveryClient
public class TeamServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamServiceApplication.class, args);
	}

}
