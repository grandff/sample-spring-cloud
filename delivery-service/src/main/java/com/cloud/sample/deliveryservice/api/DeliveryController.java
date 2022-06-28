package com.cloud.sample.deliveryservice.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cloud.sample.deliveryservice.api.dto.DeliveryResponseData;
import com.cloud.sample.deliveryservice.service.DeliveryService;

@RestController
public class DeliveryController {

    private final DeliveryService deliveryService;

    @Autowired
    public DeliveryController(DeliveryService deliveryService){
        this.deliveryService = deliveryService;
    }

    // ping 확인
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }

    // order service를 통해 호출
    @GetMapping("/request/{userId}/{address}")
    public DeliveryResponseData getDelivery(@PathVariable("userId") String userId, @PathVariable("address") String address){
        return deliveryService.save(userId, address);
    }

}
