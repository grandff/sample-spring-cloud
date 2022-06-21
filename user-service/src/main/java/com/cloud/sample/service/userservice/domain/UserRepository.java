package com.cloud.sample.service.userservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cloud.sample.service.userservice.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

}