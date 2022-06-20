package com.cloud.sample.service.userservice.storage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

// 비지니스 로직 담당
@Service
@Transcational
public class UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRespository){
        this.userRepository = userRepository;
    }

    /*
     * 사용자 저장
     * @param userCreateData 저장하려는 사용자 이름
     * @return 저장된 사용자
     */

     /*
      * 사용자 조회
      * @param id 사용자 id
      * @return 저장된 사용자와 팀 정보
      * @throws RuntimeException
      */
}