# MEMBER-SERVICE
로그인, 회원가입 등 회원 서비스

## api list
1. 


## 구현 순서
1. domain 기본 클래스 생성
    > Member.java
2. dto 생성 (주고받을 데이터 형식)
    > request, response 나눠서 설정
3. repository 생성
4. service 생성
5. controller 생성
6. openfeign 추가(필요시)
7. 테스트

## 로그인 관련 메모중 ..
- login request dto 생성
- spring security를 통해 구현
- security config 먼저 설정해야함
    1. spring security 기본 설정하기 
    2. config에 커스텀 필터를 사용하기 위해 메소드 생성
    > SecurityConfig.java
- BCryptPasswordEncoder bean 등록
    > MemberServiceApplication.java
- 로그인 요청 시 호출되서 계정 정보를 받아 인증까지 처리하는 filter method 생성(override)
    > AuthenticationFilter.java
- 토큰 처리 메서드 추가
    > TokenProvider.java
- service에서 db 조회를 하기 위한 메서드 추가
    > MemberService.java

## 참고자료
[exception 핸들러](https://jyami.tistory.com/55)<br/>
[security 로그인 인증 구현](https://velog.io/@bum12ark/MSA-JWT-%EC%9D%B8%EC%A6%9D-%EC%84%9C%EB%B2%84-%EA%B5%AC%EC%B6%95%ED%95%98%EA%B8%B0-1.-%EB%A1%9C%EA%B7%B8%EC%9D%B8)<br/>
[security user custom example](https://derekpark.tistory.com/42)