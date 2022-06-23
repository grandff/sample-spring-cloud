# SAMPLE-SERVICE

## 기본구성
- eureka client
- query dsl
- jpa
- h2 database(test)
- mysql(dev)
- spring bus
- actuator
- config
- feign client
- web
- lombok
- openapi docs
- jwt
> egov common package는 일단 제외

## 구현순서
1. sample-service 복사 후 이름 변경
    > 프로젝트이름, 패키지경로 등등
    > 1을 안할 경우 gradle project 생성
2. config server에 yml 파일 추가
    > SERVICE_NAME-service.yml / SERVICE_NAME-service-test.yml / SERVICE_NAME-service-prod.yml ...
3. gateway server에 route 추가
4. build 후 ping 테스트
    > wrapper : gradle wrapper
    > clean : ./gradlew clean
    > build : ./gradlew build -x test(테스트 제외)
    > run : java -jar build/libs/JAR_NAME.jar