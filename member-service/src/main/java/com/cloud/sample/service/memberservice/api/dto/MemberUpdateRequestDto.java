package com.cloud.sample.service.memberservice.api.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberUpdateRequestDto {
    @NotBlank(message = "사용자 이름은 필수입니다.")    
    private String userName;

    @NotBlank(message = "이메일은 필수입니다.")    
    @Email
    private String email;

    @Pattern(regexp = "((?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20})|()",
            message = "패스워드 형식이 일치하지 않습니다.")
    private String password;

    private String tel;

    @Builder
    public MemberUpdateRequestDto(String userName, String email, String tel, String password){
        this.userName = userName;
        this.email = email;
        this.tel = tel;
        this.password = password;
    }
}
