package com.cloud.sample.service.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.cloud.sample.service.userservice.api.dto.DeliveryResponseData;
import com.cloud.sample.service.userservice.api.dto.TeamResponseData;
import com.cloud.sample.service.userservice.api.dto.UserCreateData;
import com.cloud.sample.service.userservice.api.dto.UserResponseData;
import com.cloud.sample.service.userservice.client.OrderServiceClient;
import com.cloud.sample.service.userservice.client.TeamServiceClient;
import com.cloud.sample.service.userservice.domain.User;
import com.cloud.sample.service.userservice.domain.UserRepository;

// 비지니스 로직 담당
@Service
@Transactional  // begin, commit 자동 수행, 예외 발생 시 rollback 등 
public class UserService{

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;    // resttemplate 의존성 추가
    private final TeamServiceClient teamServiceClient;
    private final OrderServiceClient orderServiceClient;

    @Autowired
    public UserService(UserRepository userRepository, RestTemplate restTemplate, TeamServiceClient teamServiceClient, OrderServiceClient orderServiceClient){
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.teamServiceClient = teamServiceClient;
        this.orderServiceClient = orderServiceClient;
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

        /**
         * Rest Template 방식
         */
        // rest template을 이용해서 team-service의 getTeamByUserId() 호출
        /*String url = String.format("http://team-service/%s/teams", id); // rest template을 bean으로 주입할 때 @LoadBalanced 어노테이션을 추가했기 때문에 microservice 이름 체계를 이용
        ResponseEntity<TeamResponseData> responseData = restTemplate.exchange(url, HttpMethod.GET, null, TeamResponseData.class);
        TeamResponseData team = responseData.getBody();*/

        /**
         * Feign 방식
         */
        TeamResponseData team = teamServiceClient.getTeam(id);

        return UserResponseData.builder()
            .userId(userOptional.getId())
            .username(userOptional.getUsername())
            .team(team) // from team-service로부터 조회한 team 정보를 담아서 반환
            .build();
      }
      
      // order service 호출
      public DeliveryResponseData getOrderRequest(String userId, String address) {
    	  DeliveryResponseData orderData = orderServiceClient.requestDelivery(userId, address);    	  
    	  return orderData;
      }
}