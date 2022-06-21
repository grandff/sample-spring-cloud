package com.cloud.sample.service.userservice.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

@Getter
@Setter
@ToString
@Builder
public class UserResponseData{
    private Long userId;
    private String username;
    private TeamResponseData team;
}