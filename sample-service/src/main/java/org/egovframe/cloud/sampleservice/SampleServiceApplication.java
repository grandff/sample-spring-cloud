package org.egovframe.cloud.sampleservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.msa.user.UserServiceApplication;

/**
 * org.egovframe.cloud.userservice.UserApplication
 * <p>
 * 유저 서비스 어플리케이션 클래스
 * Eureka Client 로 설정했기 때문에 Eureka Server 가 먼저 기동되어야 한다.
 *
 * @author 표준프레임워크센터 jaeyeolkim
 * @version 1.0
 * @since 2021/06/30
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------    ---------------------------
 *  2021/06/30    jaeyeolkim  최초 생성
 * </pre>
 */
@ComponentScan({"org.egovframe.cloud.common", "org.egovframe.cloud.servlet", "org.egovframe.cloud.userservice"}) // org.egovframe.cloud.common package 포함하기 위해
@EntityScan({"org.egovframe.cloud.servlet.domain", "org.egovframe.cloud.userservice.domain"})
@EnableDiscoveryClient
@SpringBootApplication
public class SampleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}
	
	@GetMapping("/info")
	public String info(@Value("${server.port}") String port) {
		return "User service 동작 포트 : " + port;
	}
	
	@GetMapping("/auth")
	public String auth(@RequestHeader(value="token") String token) {
		return "token is " + token;
	}
	
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
