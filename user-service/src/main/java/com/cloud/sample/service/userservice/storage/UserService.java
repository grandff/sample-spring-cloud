package com.cloud.sample.service.userservice.storage;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.cloud.sample.service.userservice.storage.UserRepository;
import com.cloud.sample.service.userservice.model.UserResponseData;
import com.cloud.sample.service.userservice.model.UserCreateData;
import com.cloud.sample.service.userservice.model.User;

// 비지니스 로직 담당
@Service
@Transactional  // begin, commit 자동 수행, 예외 발생 시 rollback 등 
public class UserService{
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /*
     * 사용자 저장
     * @param userCreateData 저장하려는 사용자 이름
     * @return 저장된 사용자
     */
    public UserResponseData save(UserCreateData userCreateData){
        User user = User.builder()  // build 패턴 사용
            .username(userCreateData.getUsername())
            .build();
        user = userRepository.save(user);

        // rest template or feign client

        return UserResponseData.builder()
            .userId(user.getId())
            .username(user.getUsername())
            .team(null) // rest template or feign client
            .build();
    }

     /*
      * 사용자 조회
      * @param id 사용자 id
      * @return 저장된 사용자와 팀 정보
      * @throws RuntimeException
      */
      public UserResponseData getUserById(Long id){
        User userOptional = userRepository.findById(id)
            .orElseThrow(RuntimeException::new);

        // rest template or feign client

        return UserResponseData.builder()
            .userId(userOptional.getId())
            .username(userOptional.getUsername())
            .team(null) // from team-service
            .build();
      }
}