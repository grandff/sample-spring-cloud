package com.cloud.sample.service.memberservice.domain;

//import com.cloud.sample.service.memberservice.domain.Role;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
public class Member{
    // 일련번호, 아이디, 비밀번호, 이름, 전화번호, 이메일, 상태코드, 새로운토큰정보, 로그인실패횟수, 로그인일자

    @Id @GeneratedValue
    @Column(name="user_no")
    private Long id;

    @Column(name="user_id", nullable=false, unique=true)
    private String userId;

    @Column(name="password", nullable=false, length=100)
    private String password;

    @Column(name="user_name", nullable=false, length=10)
    private String userName;

    @Column(name="tel")
    private String tel;

    @Column(name="email", nullable=false, length=100, unique=true)
    private String email;

    private String refreshToken;

    @Column(nullable = false, length=20, columnDefinition="varchar(20) default '01'")
    private String userStateCode;   // 상태

    @Column(nullable=false, columnDefinition="tinyint default '0'")
    private Integer loginFailCount;

    @Column
    private LocalDateTime lastLoginDate;

    @Column(nullable = false)
    private String role;
    
    //@Enumerated(EnumType.STRING)    // enum 값을 string 문자열로 저장
    //@Column(name="role_id", nullable = false)
    //private Role roleId;

    
    @Builder
    public Member(String userId, String password, String userName, String tel, String email, String userStateCode, String role){
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.tel = tel;
        this.email = email;
        this.userStateCode = userStateCode;
        this.role = role;
    }

    // 사용자 refresh token 정보 입력
    public Member updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
        return this;
    }

    // 로그인 실패 시 로그인 실패수 증가
    public Member failLogin(){
        this.loginFailCount = loginFailCount + 1;
        if(this.loginFailCount >= 5){
            this.userStateCode = MemberStateCode.HALT.getKey();   // ??
        }
        return this;
    }

    // 로그인 성공 시 로그인실패수와 마지막로그인일시 갱신
    public Member successLogin(){
        this.loginFailCount = 0;
        this.lastLoginDate = LocalDateTime.now();
        return this;
    }

    // 정보 수정
    public Member update(String userName, String password, String email, String tel){
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.tel = tel;

        return this;
    }
}