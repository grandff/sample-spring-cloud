package com.cloud.sample.service.memberservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cloud.sample.service.memberservice.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{
    Optional<Member> findByUserId(String userId);   // 아이디를 통해 가입 여부 확인
    Optional<Member> findByEmailAndUserId(String email, String userId); // 아이디와 이메일을 통해 가입 여부 확인
}