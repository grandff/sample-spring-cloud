package com.cloud.sample.service.memberservice.service;

import java.util.ArrayList;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cloud.sample.service.memberservice.api.dto.MemberJoinRequestDto;
import com.cloud.sample.service.memberservice.api.dto.MemberResponseDto;
import com.cloud.sample.service.memberservice.api.dto.MemberUpdateRequestDto;
import com.cloud.sample.service.memberservice.common.CommonMessageException;
import com.cloud.sample.service.memberservice.domain.Member;
import com.cloud.sample.service.memberservice.domain.MemberRepository;

import lombok.RequiredArgsConstructor;

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

        // 사용자 존재여부 확인
        Member member = memberRepository.findByUserId(userId)
                        .orElseThrow(() -> new CommonMessageException("해당 사용자가 존재하지 않습니다."));
        
        // 권한 설정
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        String roleId = member.getRole();
        authorities.add(new SimpleGrantedAuthority(roleId)); // 별도 권한 설정한게 없으면 아무거나 넣어주거나 아예 empty list 를 줘야함. 빈 string 넣으면 error 

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
    
    // 사용자 정보 수정
    @Transactional
    public String updateMember(String userId, MemberUpdateRequestDto requestDto){        
        // 일단 사용자 entity를 가져온다음에
        Optional<Member> member = memberRepository.findByUserId(userId);
        if(!member.isPresent()){
            throw new CommonMessageException("사용자가 존재하지 않습니다.");
        }

        // 새로 변경할 패스워드가 있으면 가져오고 없으면 기존꺼 사용
        final String password = requestDto.getPassword() != null && !"".equals(requestDto.getPassword()) 
            ? passwordEncoder.encode(requestDto.getPassword())
            : member.get().getPassword();

        // valid 체크가 안되어있는 것들은 기존 데이터 그대로 사용
        final String tel = requestDto.getTel() != null && !"".equals(requestDto.getTel())
            ? requestDto.getTel() : member.get().getTel();
                
        // 정보 수정 처리
        member.get().update(requestDto.getUserName(), password, requestDto.getEmail(), tel);
        
        return userId;
    }

    // 이메일로 사용자 조회
    public String findByEmail(String email){
        Member member = memberRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("해당 사용자는 존재하지 않습니다"));

        return member.getUserId();
    }

    // 전화번호로 사용자 조회
    public String findByTel(String tel){
        Member member = memberRepository.findByTel(tel)
            .orElseThrow(() -> new UsernameNotFoundException("해당 사용자는 존재하지 않습니다."));

        return member.getUserId();
    }

    // 비밀번호 찾기
    // 하려고 했는데 jpa 방식으로  하면 할게 많아서 일단 패스요..
    /*
    @Transactional
    public Boolean findPassword(MemberFindPasswordRequestDto requestDto){
        // 이메일주소와 아이디로 유저가 존재하는지 확인
        Member member = memberRepository.findByEmailAndUserId(requestDto.getEmail(), requestDto.getUserId())
            .orElseThrow(() -> new UsernameNotFoundException("해당 사용자가 존재하지 않습니다."));

        // 원래는 이메일 전송이지만 여기서는 콘솔에 토큰값 로그만 찍을거임
        final String tokenValue = UUID.randomUUID().toString().replaceAll("-", "");
        System.out.println(tokenValue);

        // 토큰값을 repository에 저장

        // 완료 후 true 리턴
    }
    */
}