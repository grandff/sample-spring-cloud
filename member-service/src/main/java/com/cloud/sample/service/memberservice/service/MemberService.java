package com.cloud.sample.service.memberservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.cloud.sample.service.memberservice.api.dto.MemberJoinRequestDto;
import com.cloud.sample.service.memberservice.api.dto.MemberResponseDto;
import com.cloud.sample.service.memberservice.domain.MemberRepository;
import com.cloud.sample.service.memberservice.domain.Member;

// 비지니스 로직 담당
@Service
@Transactional  // begin, commit 자동 수행, 예외 발생 시 rollback 등 
@RequiredArgsConstructor
public class MemberService{
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // 아이디로 사용자 조회
    public MemberResponseDto findByUserId(String userId){
        Member member = memberRepository.findByUserId(userId)
                        .orElseThrow(() -> new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."));   // 없을 경우 spring security의 exception 호출
        return new MemberResponseDto(member);
    }

    // 아이디 또는 이메일 중복 확인
    public Boolean existsUser(String email, String userId){
        // null check
        if(String.valueOf(email).equals("") || String.valueOf(userId).equals("")){
            throw new RuntimeException("이메일 또는 아이디가 없습니다.");
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
            throw new RuntimeException("이미 등록된 아이디 입니다.");
        }

        // 사용자 객체 생성
        Member member = requestDto.toEntity(passwordEncoder);
        
        // 저장
        memberRepository.save(member);

        return true;
    }
}