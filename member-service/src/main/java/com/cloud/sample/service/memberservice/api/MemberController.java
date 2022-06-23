package com.cloud.sample.service.memberservice.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class MemberController{
    
    @GetMapping("/ping")   
    public String ping(){
        return "pong";
    }
}