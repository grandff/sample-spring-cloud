package com.cloud.sample.service.memberservice.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;
import com.cloud.sample.service.memberservice.domain.Member;

@Getter
@Setter
@ToString
public class MemberResponseDto{
    private Long id;
    private String userId;
    private String password;
    private String userName;
    private String tel;
    private String email;

    // entity 중 일부만 사용하므로 entity를 받아서 필드에 값을 넣을거임
    // 만약 전부를 쓰면 이렇게 안해도 되는듯..?
    public MemberResponseDto(Member entity){
        this.id = entity.getId();
        this.userId = entity.getUserId();
        this.password = entity.getPassword();
        this.email = entity.getEmail();
        this.userName = entity.getUserName();
        this.tel = entity.getTel();
    }
}