package com.cloud.sample.sampleservicemaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.cloud.sample.sampleservicemaven"})
//@EntityScan({"com.cloud.sample.service.sampleservice.domain"})
//@EnableJpaRepositories(basePackages = {"com.cloud.sample.service.sampleservice"})
@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients(basePackages = {"com.cloud.sample.service.sampleservice.client"})
public class SampleServiceMavenApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleServiceMavenApplication.class, args);
	}

}
