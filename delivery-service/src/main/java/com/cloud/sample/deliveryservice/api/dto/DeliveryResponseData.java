package com.cloud.sample.orderservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponseData {
    private String orderNum;
    private String userId;
    private String address;
}
