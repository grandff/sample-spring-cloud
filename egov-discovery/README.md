# netflix eureka server

## 개요
서버를 구성 및 배포하여 가용성이 높도록 할 수 있으며, 각 서버는 등록된 서비스에 대한 상태를 다른 서버로 복제할 수 있게 하는 역할을 함

## 종류
### Service Discovery(Spring Cloud Netflix - Eureka Server)
각각의 서비스의 위치가 등록된 서버에서 특정 작업을 위한 서버의 위치를 파악하는 작업

### Service Registry(Spring Cloud Netflix - Eureka Client)
각각의 서비스가 자신의 위치(IP) 정보를 특정 서버에 등록 Registry 하는 작업

## 동작 흐름
1. Service Registry 기능을 할 Eureka Server 기동
2. Service Registry 서버인 Eureka Server 에 등록될 서비스들이 기동
	- 여기서 등록된 서비스는 Eureka Client 라고 함
3. Eureka 서버는 자신에게 등록된 Eureka Client 에게 30초마다 Ping을 보내며 Health Checking 수행
4. 만약 30초마다 보내는 Heart Heat가 일정 횟수 이상으로 동작되지 않으면 Eureka Server는 해당 Client를 삭제

Eureka는 단지 서비스의 위치만을 표현하는 역할로 보통 Spring Cloud Gateway나 Netflix Zuul 과 같은 Gateway 서비스나 Ribbon 과 같은 클라이언트 사이드 로드밸런서와 함께 동작

## 예제 구성
1. Service Discovery Server
	- Discovery Application(Spring Cloud Eureka-Server)
2. Microservices
	- UserService(Spring Cloud Eureka-Client)
	- OrderService(Spring Cloud Eureka-Client)

## 개발 흐름
1. boot 프로젝트 생성(https://start.spring.io/)
	- gradle, java11
	- Spring Eureka Server 의존성 추가
2. application.yml 작성(properties를 yml로 변경)
3. DiscoveryApplication에 @EnableEurekaServer 등록
4. 이후 앱 실행해서 정상동작 확인
5. 이후 micro service 개발로 넘어감 (user, order service 개발 및 실행)
6. 위에서 추가한 micro service가 dashboard에 뜨는지 확인
7. 각 서비스를 들어가서 /info를 호출했을때 정상적으로 데이터가 나오면 성공

## 추가 설정
1. spring security 의존성 추가
	- 접속 계정 설정
2. yml 설정 추가
	- defaultZone 설정