# Configuration Server
## 개요
- 분산 시스템에서 사용되는 환경설정을 Configuration 서버에서 관리할 수 있도록 환경 제공
- 각각 시스템에 중복으로 존재했던 설정들을 Configuartion 서버에서 일원화 하여 체계적으로 관리
- 무중단 설정 변경이 가능하여 기존처럼 설정이 변경될때마다 연관된 시스템의 배포가 필요 없음
- DB 접속 정보나 패스워드 같은 민감한 정보들을 노출할 필요가 없어서 보안적인 측면 개선

## 구현순서
### 1. Applciation 설정
- main class에 @EnableConfigServer 선언을 통해 ConfigServer 활성화
### 2. bootstrap 작성
- application.properties 삭제
- bootstrap.yml(공통 환경설정) 과 bootstrap-local.yml(local 환경설정) 추가 
    - local을 따로 두는 이유? 환경설정 파일은 local에 둬서 마음대로 수정하면서 개발하기 위해
    - ${user.home}은 사용자의 홈 디렉토리를 의미함
    - 운영체제마다 경로가 다르므로 맞춰서 설정
### 3. 서비스별 환경 설정 추가
- bootstrap-local.yml에 추가한 경로에 맞게 설정파일 추가