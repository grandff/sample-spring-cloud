package com.cloud.sample.service.memberservice.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// 로그인시 필요정보만 담긴 dto
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberLoginRequestDto{
    
    private String userId;  // 아이디    
    private String password; // 비밀번호
    private String token; // 토큰
    private String userName; // 이름

    // 생성자
    @Builder
    public MemberLoginRequestDto(String userId, String password, String token, String userName){
        this.userId = userId;
        this.password = password;
        this.token = token;
        this.userName = userName;
    }

    // oauth 셋팅
    public void setOAuthLoginInfo(String userId, String password){
        this.userId = userId;
        this.password = password;
    }
}