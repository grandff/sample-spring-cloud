package com.cloud.sample.service.memberservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

//import com.cloud.sample.service.memberservice.domain.Role;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
public class Member{
    // 일련번호, 아이디, 비밀번호, 이름, 전화번호, 이메일, 상태코드, 새로운토큰정보, 로그인실패횟수

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
    
    //@Enumerated(EnumType.STRING)    // enum 값을 string 문자열로 저장
    //@Column(name="role_id", nullable = false)
    //private Role roleId;

    
    @Builder
    public Member(String userId, String password, String userName, String tel, String email, String userStateCode){
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.tel = tel;
        this.email = email;
        this.userStateCode = userStateCode;
    }
}