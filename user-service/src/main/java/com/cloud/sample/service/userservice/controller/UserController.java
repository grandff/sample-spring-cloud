package com.cloud.sample.service.userservice.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cloud.sample.service.userservice.storage.UserService;
import com.cloud.sample.service.userservice.model.UserResponseData;
import com.cloud.sample.service.userservice.model.UserCreateData;

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

    @PostMapping("/users")   
    public UserResponseData createUser(@RequestBody UserCreateData userCreateData){
        return userService.save(userCreateData);
    }

    @PostMapping("/users/{userId}")  
    public UserResponseData getUser(@PathVariable("userId") Long id){
        return userService.getUserById(id);
    }

}