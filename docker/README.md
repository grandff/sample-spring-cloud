# docker

## mysql
### 명령어
1. docker network create egov-network
2. docker mysql 경로로 이동
3. docker-compose up -d
4. docker exec -it mysql bash   
    > 컨테이너 내부 접속
5. mysql -u msasample -p
    > 패스워드도 msasample

## rabbitmq
1. docker run -d -e TZ=Asia/Seoul --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
2. 15672 포트로 접속
3. guest, guest 로그인