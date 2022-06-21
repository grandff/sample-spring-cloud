package com.cloud.sample.service.userservice.model;

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