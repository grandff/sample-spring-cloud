# USER-SERVICE

## REST TEMPLATE 구현 순서
1. Bean 추가
2. UserService에서 RestTemplate 의존성 주입
3. 서비스에서 RestTemplate으로 호출

## OpenFeign 구현 순서
1. 의존성 추가
    > org.springframework.cloud:spring-cloud-starter-openfeign
2. HTTP Endpoint에 대한 Client 인터페이스 생성
    > FeignClient라는 어노테이션을 이용해 Eureka 에 등록된 Instance이름을 찾아서 매핑시킴
    > 만약 못찾는다? MainApplication에 basepackage 명시
3. 호출

## OpenFeign 에러 반환 처리
1. ErrorDecoder 인터페이스를 상속받는 클래스 생성

## OpenFeign 참조할만한 사이트
- https://techblog.woowahan.com/2630/