package com.cloud.sample.service.userservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cloud.sample.service.userservice.api.dto.TeamResponseData;

// http endpoint에 대한 client 인터페이스
@FeignClient(name = "team-service")
public interface TeamServiceClient{
    @GetMapping("/{userId}/teams")  // user-service가 호출해야할 endpoint 작성  (/team/{userId}/teams)
    TeamResponseData getTeam(@PathVariable("userId") Long id);
}