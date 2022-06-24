package com.cloud.sample.service.memberservice.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Builder
public class Member{
    // 일련번호, 아이디, 비밀번호, 이름, 전화번호, 이메일

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

}