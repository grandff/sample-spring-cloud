package com.cloud.sample.service.userservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cloud.sample.service.userservice.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

}