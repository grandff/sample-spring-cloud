package com.cloud.sample.service.memberservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cloud.sample.service.memberservice.api.dto.MemberJoinRequestDto;
import com.cloud.sample.service.memberservice.api.dto.MemberResponseDto;
import com.cloud.sample.service.memberservice.domain.MemberRepository;
import com.cloud.sample.service.memberservice.domain.Member;

import com.cloud.sample.service.memberservice.common.CommonException;
import com.cloud.sample.service.memberservice.common.CommonMessageException;
import com.cloud.sample.service.memberservice.common.dto.ErrorCode;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

// 비지니스 로직 담당
@Service
@Transactional  // begin, commit 자동 수행, 예외 발생 시 rollback 등 
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 아이디로 사용자 조회
    public MemberResponseDto findByUserId(String userId){
        Member member = memberRepository.findByUserId(userId)
                        .orElseThrow(() -> new UsernameNotFoundException("사용자 정보가 없습니다."));   // 없을 경우 spring security의 exception 호출
        return new MemberResponseDto(member);
    }

    // 아이디 또는 이메일 중복 확인
    public Boolean existsUser(String email, String userId){
        // null check
        if(String.valueOf(email).equals("") || String.valueOf(userId).equals("")){
            throw new CommonMessageException("이메일 또는 아이디를 입력하세요.");
        }

        // ispresent 
        return memberRepository.findByEmailAndUserId(email, userId).isPresent();
    }

    // 사용자 회원 가입
    @Transactional
    public Boolean join(MemberJoinRequestDto requestDto){
        // 사용자 중복 여부 확인
        boolean exists =existsUser(requestDto.getEmail(), requestDto.getUserId());
        if(exists){
            throw new CommonMessageException("이미 존재하는 회원입니다.");
        }

        // 사용자 객체 생성
        Member member = requestDto.toEntity(passwordEncoder);
        
        // 저장
        memberRepository.save(member);

        return true;
    }

    // 스프링 시큐리티에 의해 로그인 대상 사용자의 패스워드와 권한 정보를 DB에서 조회해서 UserDetails를 리턴
    // SecurityConfig > configure > UserDetaisService 메소드에서 호출
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException{
        // 로그인 실패시 로그에 남기기 위해 설정
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        request.setAttribute("userId", userId);
        System.out.println("아이디 정보 :: " + userId);

        // 사용자 존재여부 확인
        Member member = memberRepository.findByUserId(userId)
                        .orElseThrow(() -> new CommonMessageException("해당 사용자가 존재하지 않습니다."));
        
        // 권한 설정
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("All")); // 별도 권한 설정한게 없으면 아무거나 넣어주거나 아예 empty list 를 줘야함. 빈 string 넣으면 error 

        // 사용자 리턴
        return new User(member.getUserId(), member.getPassword(), authorities);
    }

    // 로그인후처리
    @Transactional
    public void loginCallback(String userId, Boolean successAt, String failContent){
        // 사용자 존재여부 확인
        Member member = memberRepository.findByUserId(userId)
                        .orElseThrow(() -> new CommonMessageException("해당 사용자가 존재하지 않습니다."));

        if(Boolean.TRUE.equals(successAt)){
            member.successLogin();
        }else{
            member.failLogin();
        }

        // 로그인로그입력처리? 필요시
    }

    // 사용자 refresh token 입력
    @Transactional
    public void updateRefreshToken(String userId, String updateRefreshToken){
        // 사용자 존재여부 확인
        Member member = memberRepository.findByUserId(userId)
                        .orElseThrow(() -> new CommonMessageException("해당 사용자가 존재하지 않습니다."));

        member.updateRefreshToken(updateRefreshToken);        
    }

    // 토큰으로 사용자 조회
    public Member findByRefreshToken(String refreshToken){
        return memberRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new CommonMessageException("해당 사용자가 존재하지 않습니다."));
    }
    
}