package com.cloud.sample.deliveryservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliveryResponseData {
    private String orderNum;
    private String userId;
    private String address;
}
