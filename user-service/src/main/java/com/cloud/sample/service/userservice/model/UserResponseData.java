package com.cloud.sample.service.userservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserResponseData{
    private Long userId;
    private String username;
    private TeamResponseData team;
}