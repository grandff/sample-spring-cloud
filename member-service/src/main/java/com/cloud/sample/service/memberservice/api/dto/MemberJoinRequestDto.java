package com.cloud.sample.service.memberservice.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.cloud.sample.service.memberservice.domain.Member;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberJoinRequestDto{
    // 아이디
    @NotBlank(message="아이디는 필수입니다.")
    private String userId;

    // 사용자이름
    @NotBlank(message="이름은 필수입니다.")
    private String userName;
    
    // 패스워드 형식 체크
    // 숫자, 영문, 특수문자, 공백제거, 자리수 8 ~ 20
    @Pattern(regexp="(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message="패스워드 형식을 확인해주세요.")
    @NotBlank(message="패스워드는 필수입니다.")
    private String password;

    // 이메일 형식 체크
    @NotBlank(message="이메일은 필수입니다.")
    private String email;

    private String tel;
    
    @Builder
    public MemberJoinRequestDto(String userId, String userName, String password, String email, String tel){
        this.userId = userId;
        this.userName = userName;        
        this.password = password;
        this.email = email;        
        this.tel = tel;
    }

    // member entity
    public Member toEntity(BCryptPasswordEncoder passwordEncoder){
        return Member.builder()
                .userId(userId)
                .userName(userName)
                .password(passwordEncoder.encode(password)) // 패스워드 인코딩
                .tel(tel)
                .email(email)
                .build();
    }
}