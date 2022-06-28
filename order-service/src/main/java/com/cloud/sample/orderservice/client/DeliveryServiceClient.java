package com.cloud.sample.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cloud.sample.orderservice.api.dto.DeliveryResponseData;

@FeignClient(name="delivery-service")
public interface DeliveryServiceClient {
    // 사용자가 넣은 주문을 다시 delivery-service를 호출해서 접수번호를 받음
    @GetMapping("/request/{userId}/{address}")
    DeliveryResponseData requestDelivery(@PathVariable("userId") String userId, @PathVariable("address") String address);
}
