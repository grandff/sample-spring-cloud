package com.cloud.sample.orderservice.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.sample.orderservice.api.dto.DeliveryResponseData;
import com.cloud.sample.orderservice.service.OrderService;

@RestController
public class OrderController {
    
    private final OrderService orderService;
    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }
    
    // delivery service 호출
    @GetMapping("/request/{userId}/{address}")
    public DeliveryResponseData requestDeliveryService(@PathVariable("userId") String userId, @PathVariable("address") String address){
        return orderService.getDeliveryRequest(userId, address);
    }

    // ping 확인
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
