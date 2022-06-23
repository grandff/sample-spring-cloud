package com.cloud.sample.service.sampleservice.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Value;

@RestController
public class SampleController{
    
    @GetMapping("/ping")   
    public String ping(){
        return "pong";
    }	
}