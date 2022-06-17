# Spring Cloud Gateway

## 개요
간편하면서도 효과적인 API 라우팅 방법을 제공하고, 보안 모니터링, 메트릭, 복원력 문제를 해결하는 것을 목표

## 주요 용어
### Route
목적지의 URI와 Predicates라는 조건들의 목록 그리고 필터들을 이용하여 어떤 곳으로 Routing 할 것인지를 명시하는 역할

### Predicate
조건(아래 예시) <br/>
```
predicated: -Path=/user/**
```

### Filter
들어오는 요청과 응답, Request, Response을 특정 필터를 타게 함으로 우리가 원하는 방식으로 요청을 보내거나 헤더를 조작할 수 있고, 해당 필터를 이용해서 로그 파일을 작성

## 동작 흐름
1. Client 는 Spring Cloud Gateway 에 요청
2. Gateway Handler Mapping 에서 해당 요청에 대한 Route와 Predicates가 일치한다고 판단하면 해당 요청은 Gateway Web handler로 보내짐
3. handler 에서 Filter Chain 을 이용해서 사전 필터 혹은 사후 필터로 나누어 동작
4. 필터링이 된 후 실제 마이크로서비스에게 전달

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
3. 앱 실행해서 정상동작 확인
4. 각각의 서비스를 Gateway에 연결
	- application.yml에 routes 항목 추가

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