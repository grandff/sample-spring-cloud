# MEMBER-SERVICE
로그인, 회원가입 등 회원 서비스

## api list
0. 상태체크(/member/actuator/helath-info)
1. 회원가입(/member/api/join)
2. 로그인(/member/login)
3. 토큰 재발급()
4. 회원정보 수정
5. 회원탈퇴

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

## 로그인 서비스 동작 순서(Spring Security)
1. /login으로 userId, password 파라미터 받음(login filter를 별도로 설정 안했기때문에 /login으로 가는..듯..?)
2. AuthenticationFilter.java의 attemptAuthentication 호출
    - request parameter로 dto 설정
3. MemberService.java의 loadUserByUsername 호출
    - 아이디, 패스워드로 일치여부 확인
    - 권한설정(spring security에서 User를 리턴하려면 설정해줘야함)
4. 이후 처리
    1. 로그인 성공
        1. AuthenticationFilter.java의 successfulAuthentication 호출
        2. TokenProvider.java의 createTokenAndAddHeader 호출
            - 토큰 생성 후 header에 셋팅해줌
            - MemberService.java의 updateRefreshToken를 호출해서 refresh token 생성
        3. MemberService.java의 loginCallback 호출
            - Member.java의 successLogin 호출
    2. 로그인 실패
        1. AuthenticationFilter.java의 unsuccessfulAuthentication 호출
        2. MemberService.java의 loginCallback 호출
            - Member.java의 failLogin 호출

## 토큰 재생성 요청 시

## 이후 인증 처리 과정
gateway server에서 해줌..!!(이제 해야함ㅋ)

## 참고자료
[exception 핸들러](https://jyami.tistory.com/55)<br/>
[security 로그인 인증 구현](https://velog.io/@bum12ark/MSA-JWT-%EC%9D%B8%EC%A6%9D-%EC%84%9C%EB%B2%84-%EA%B5%AC%EC%B6%95%ED%95%98%EA%B8%B0-1.-%EB%A1%9C%EA%B7%B8%EC%9D%B8)<br/>
[security user custom example](https://derekpark.tistory.com/42)