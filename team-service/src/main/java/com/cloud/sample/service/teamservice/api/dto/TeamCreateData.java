package com.cloud.sample.service.teamservice.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TeamCreateData{
    private String name;
    private String address;
}