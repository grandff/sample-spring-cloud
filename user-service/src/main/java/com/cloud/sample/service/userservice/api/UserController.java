package com.cloud.sample.service.userservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.sample.service.userservice.api.dto.DeliveryResponseData;
import com.cloud.sample.service.userservice.api.dto.UserCreateData;
import com.cloud.sample.service.userservice.api.dto.UserResponseData;
import com.cloud.sample.service.userservice.service.UserService;

//@Component
@RestController
public class UserController{
    // 서비스 생성자 주입
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/ping")   
    public String ping(){
        return "pong";
    }
    
    @GetMapping("/users")   // 테스트를 위해 get으로 변경(codespace 문제)
    public UserResponseData createUser(@RequestParam("username") String username){
        UserCreateData userCreateData = new UserCreateData();
        userCreateData.setUsername(username);
        return userService.save(userCreateData);
    }

    @GetMapping("/users/{userId}")     // 테스트를 위해 get으로 변경(codespace 문제)
    public UserResponseData getUser(@PathVariable("userId") Long id){
        return userService.getUserById(id);
    }
    
    // zipkin test
    @GetMapping("/getorder/{userId}/{address}")
    public DeliveryResponseData getOrder(@PathVariable("userId") String userId, @PathVariable("address") String address) {
    	return userService.getOrderRequest(userId, address);
    }

}