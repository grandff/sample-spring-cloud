# netflix eureka client

## 개요
- 마이크로서비스 1 (user)
- 일반적인 spring boot로 동작해야하기 때문에 아래의 의존성이 꼭 들어가야함
	- spring web, lombok, eureka client
	
## 개발 흐름
1. application.yml 작성
2. EnableDiscoveryClient, RestController 어노테이션추가
	- 서비스 동작 여부를 알 수 있도록 get mapping method 추가
3. 서비스 실행

## 실시간 반영 설정
1. 의존성 추가
	- spring-boot-starter-actuator
	- bootstrap
	