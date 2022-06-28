package com.cloud.sample.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.sample.orderservice.api.dto.DeliveryResponseData;
import com.cloud.sample.orderservice.client.DeliveryServiceClient;

@Service
@Transactional
public class OrderService {
    private final DeliveryServiceClient deliveryServiceClient;

    @Autowired
    public OrderService(DeliveryServiceClient deliveryServiceClient){
        this.deliveryServiceClient = deliveryServiceClient;
    }

    // request-service 호출
    public DeliveryResponseData getDeliveryRequest(String userId, String address){
        DeliveryResponseData deliveryData = deliveryServiceClient.requestDelivery(userId, address);
        return deliveryData;
    }
}
