# Spring Cloud Config Server

## 개요
각 서비스의 설정을 한곳에서 관리(중앙 집중식)

## 종류
### Client
서버에서 뿌려주는 설정 정보를 받아 이용

### Server
설정 정보를 모아놓고 저장하여 뿌려주는 주체

## 동작 흐름
1. 각각의 설정 정보들을 빼서 하나의 github repo로 업로드(또는 native file system에 업로드)
	- spring에서는 공식적으로 git에 올리는걸 권장하고 있음 
2. Config서버와 remote 서버 연결
3. Remote 서버와 local git stage를 서로 연결하고 push
4. 각각의 Applicatino 서버는 설정 정보를 Config 서버에 요청

## 개발 흐름
1. boot 프로젝트 생성(https://start.spring.io/)
	- gradle, java11
	- Spring Cloud Config Server 의존성 추가
2. Application에 EnableConfigServer 등록
3. 본인 PC (또는 서버)에 configuration 파일들을 저장할 디렉토리를 생성하고 message.yml 파일 생성
	- 되도록 ${HOME} 기준으로 잡기(cmder bash로 확인)
	- cd ${HOME}
	- mkdir workspace/cloud-sample-config
	- 이후 파일 생성(yml 소스코드 작성은 나중에)
4. Application.yml 수정
	- profile default는 native로 수정
	- window하고 mac하고 경로 설정 방법이 다름
	- 로컬폴더로 하는 경우 수정할때마다 config server 재기동이 필요함
5. message.yml 작성
	```yml
	
	message:
		owner: config-services's native folder
		content: 데이터입니다
	
	spring:
		datasource:
			url: dbUrl
			driver: com.h2.Driver
			username: admin
			password: password1234
			
	jwt:
		token:
			key: secret_key
		
	```
6. 이후 실행해서 확인
	- localhost:8888/message/native로 들어갔을때 파일이 보여야 정상
7. 각각 마이크로서비스에서 사용 가능하도록 설정
	- config 서버가 내려주는 설정파일들을 이용해야함
	- 이를 위해 spring-cloud-starter-bootstrap 의존성 추가가 필요
	- bootstrap.yml 생성 후 소스작성
		- 동일한 depth에 생성되야함
8. 테스트를 위해서 Request Mapping End Point 작성(user service)
> 오류가 하도 많이 나와서 어찌저찌 됐음... 

## 다른 프로필 설정 
개발환경마다 다른 프로필 적용
1. config folder에 아래와 같은 파일 추가
	- application.yml
	- user-service.yml
	- user-service-test.yml
2. 추가한 yml 파일이 맞게 나오는지 확인
	- localhost:8888/user-service/native
	- localhost:8888/user-service/test
3. user service에서 프로필마다 다른 파일을 불러오도록 설정

## 실시간 반영 설정
Actuator로 소스 코드 관리
1. 의존성 추가
	- spring-boot-starter-actuator
2. application.yml에 endpoint include
3. 요청을 보내서 확인하기
	- http://server.com/actuator/health
4. 실시간 반영을 위해 refresh도 추가
5. service로 들어가서 actuator 소스코드 작성(user-service)

## 암호화, 복호화
- {ciper}라는 문자열이 존재하면 해당 문자열은 암호화 된 것으로 판단하고 각기 다른 서버에 설정 정보를 내릴때, 해당 암호문을 복호화
- 만약 해당 암호문이 키가 일치하지 않는 등 다른 이유로 복호화가 불가능하면 n/a라는 문자열을 리턴

### 암호화 구현 방법
1. bootstrap.yml을 이용해 key store 추가
	- encrypt.key 추가
2. bootstrap 의존성 추가
	- spring-cloud-starter-bootstrap
3. bootstrap.yml에 키 저장
4. /encrypt, /decrypt 엔드 포인트를 사용해서 암호화
5. yml 파일에 암호화된 문자열을 넣고 응답으로 복호화된 문자열 받기
> native 환경에서는 안되는거 같다...

## Spring Cloud Bus
분산 시스템 환경, 마이크로 서비스 환경에서 각각의 노드나 서비스를 연결시켜주는 경량 메시지 브로커

### Spring Cloud Bus가 적용된 설정 정보 적용 과정
1. configuration file을 remote repository에 push
2. Spring Cloud Bus가 Message Broker로 변경된 설정 정보에 대한 Messaeg 발행
3. Message Broker는 설정 정보 저장
4. 개발자가 설정 정보가 변경되었음을 Config Server에 알림
5. Message Broker가 해당 메시지를 Subscribing 하고 있는 Application 들에게 Broadcasting
6. 각각의 Application은 Spring Cloud Bus가 받은 설정 정보 반영

### Rabbit MQ 실습 순서
1. 각각의 마이크로서비스에 Spring Boot Actuactor와 Spring Cloud Bus 의존성 추가
	> implementation 'org.springframework.cloud:spring-cloud-starter-bus-amqp'	// bus + rabbitmq
	> implementation 'org.springframework.boot:spring-boot-starter-actuator'	// actuator
2. application.yml 파일에서 Spring Cloud Bus와 actuactor 정보 추가
3. Rabbit MQ -> Config Service -> Micro Services 차례로 실행
4. 설정 정보 수정 후 github push(나는 그냥 native에서 변경..?)
5. Config Server의 Bus Refresh
6. 테스트

## 참고
[2.4 config legacy 설정법](https://multifrontgarden.tistory.com/278)
[spring client server 설정](https://otrodevym.tistory.com/entry/spring-boot-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0-14-spring-cloud-config-%EC%84%A4%EC%A0%95-%EB%B0%8F-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%86%8C%EC%8A%A4)