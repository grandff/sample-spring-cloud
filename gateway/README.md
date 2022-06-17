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
#### built-in Predicate Factory
- After & Before & Between
	시간과 관련된 Predicate(순서대로 이후, 이전, 특정시간 사이)<br>
- Cookie
	정규표현식을 만족하는 Cookie 값인지 확인
- Method
	HTTP method가 일치하는지 확인
- Path

### Filter
들어오는 요청과 응답, Request, Response을 특정 필터를 타게 함으로 우리가 원하는 방식으로 요청을 보내거나 헤더를 조작할 수 있고, 해당 필터를 이용해서 로그 파일을 작성
#### built-in GatewayFilter Factory
- AddRequestHeader, AddResponseHeader
- AddRequestParameter
- RewritePath

### HandlerMapping
- Gateway로 요청이 들어오면 동작
- **Gateway Handler Mapping에서는 내부에 Predicates(조건)이 맏다면 Filter를 거치게하고 Route를 함**

## 동작 흐름
1. Client 는 Spring Cloud Gateway 에 요청
2. Gateway Handler Mapping 에서 해당 요청에 대한 Route와 Predicates가 일치한다고 판단하면 해당 요청은 Gateway Web handler로 보내짐
3. handler 에서 Filter Chain 을 이용해서 사전 필터 혹은 사후 필터로 나누어 동작
4. 필터링이 된 후 실제 마이크로서비스에게 전달

## 개발 흐름
1. boot 프로젝트 생성(https://start.spring.io/)
	- gradle, java11
	- Spring Eureka Server 의존성 추가
2. application.yml 작성(properties를 yml로 변경)
3. 앱 실행해서 정상동작 확인
4. 각각의 서비스를 Gateway에 연결
	- application.yml에 routes 항목 추가
	- loadbalacner 추가
5. 그외 항목 추가
	- built in factory 항목 추가(테스트용)
	- rewritePath 필터 추가

## 추가 설정
