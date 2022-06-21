package com.cloud.sample.service.teamservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cloud.sample.service.teamservice.domain.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long>{
    TeamMember findByUserId(Long userId);
}