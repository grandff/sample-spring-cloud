package com.cloud.sample.service.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cloud.sample.service.userservice.api.dto.DeliveryResponseData;

@FeignClient(name="order-service")
public interface OrderServiceClient {
    // 사용자가 주문을 넣음
    @GetMapping("/request/{userId}/{address}")	
    DeliveryResponseData requestDelivery(@PathVariable("userId") String userId, @PathVariable("address") String address);
}
