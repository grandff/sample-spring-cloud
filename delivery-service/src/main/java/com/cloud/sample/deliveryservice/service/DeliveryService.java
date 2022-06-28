package com.cloud.sample.deliveryservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cloud.sample.deliveryservice.api.dto.DeliveryResponseData;
import com.cloud.sample.deliveryservice.domain.DeliveryRepository;
import com.cloud.sample.deliveryservice.domain.Delivery;

@Service
@Transactional
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;

    @Autowired
    public DeliveryService(DeliveryRepository deliveryRepository){
        this.deliveryRepository = deliveryRepository;
    }

    // reseponse data create
    public DeliveryResponseData save(String userId, String address){
        // 데이터 저장
        Delivery delivery = Delivery.builder()
            .orderNum("1111122222")
            .userId(userId)
            .address(address)
            .build();
        delivery = deliveryRepository.save(delivery);

        // 임의로 접수번호를 만들어서 리턴
        return DeliveryResponseData.builder()
            .orderNum(delivery.getOrderNum())
            .userId(delivery.getUserId())
            .address(delivery.getAddress())
            .build();
    }
}