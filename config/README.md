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


## 참고
[2.4 config legacy 설정법](https://multifrontgarden.tistory.com/278)
[spring client server 설정](https://otrodevym.tistory.com/entry/spring-boot-%EC%84%A4%EC%A0%95%ED%95%98%EA%B8%B0-14-spring-cloud-config-%EC%84%A4%EC%A0%95-%EB%B0%8F-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%86%8C%EC%8A%A4)