package com.cloud.sample.deliveryservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>{
    
}
