package com.cloud.sample.service.userservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeamResponseData{
    private Long teamId;
    private String name;
    private String address;
}