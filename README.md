# sample-spring-cloud
### spring cloud sample 프로젝트

## 용어 설명
### Configuration Server : 시스템의 환경 설정을 중앙화하고 실시간으로 설정을 변경하고 전파하는 기능을 제공
### API Gateway : 다양한 서비스를 사용하는 클라이언트에게 단일 경로의 api 엔드포인트를 제공하여 백단 서비스를 통합할 수 있도록 지원
### Service Discovery : 가용한 서비스 인스턴스들의 위치 정보들을 동적으로 등록하고, 클라이언트에게 제공하여 유연한 시스템 관리 및 확장 지원
### Load Balancing : 클라이언트 로드밸런싱 지원
### Circuit Breakers : Gateway에 적용하여 서비스 창에서 계단식 오류로부터 서비스를 보호하고 대체 응답을 제공할 수 있는 기능 제공

## 프로젝트 목록
### discovery : spring eureka server
### config : spring cloud config server
    - config 설정은 로컬에 저장
### gateway : spring cloud gateway server
### sample-service : micro service sample template

## 설명
- board-service : 게시판
- user-service : 회원관리
- sample-service : 기본템플릿

## 참고한 사이트
[eureka discovery server](https://wonit.tistory.com/495?category=854728)
