package com.cloud.sample.orderservice.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    
    @GetMapping("/order/request/{userId}/{address}")
    public void requestDeliveryService(@PathVariable("userId") String userId, @PathVariable("address") String address){

    }

    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
