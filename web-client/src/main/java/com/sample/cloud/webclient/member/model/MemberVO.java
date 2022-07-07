package com.sample.cloud.webclient.member.model;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ToString
public class MemberVO {
    private String userId;
    private String userName;
    private String password;
    private String email1;
    private String email2;
    private String email;
    private String tel;    
}
