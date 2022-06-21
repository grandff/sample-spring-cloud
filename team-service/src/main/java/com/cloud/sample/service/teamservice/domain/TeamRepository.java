package com.cloud.sample.service.teamservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cloud.sample.service.teamservice.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{
    Team findByName(String name);
}