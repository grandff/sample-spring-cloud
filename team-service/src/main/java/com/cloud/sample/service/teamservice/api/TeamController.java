package com.cloud.sample.service.teamservice.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.cloud.sample.service.teamservice.service.TeamService;

import com.cloud.sample.service.teamservice.api.dto.TeamResponseData;
import com.cloud.sample.service.teamservice.api.dto.TeamMemberAddData;
import com.cloud.sample.service.teamservice.api.dto.TeamCreateData;
@RestController
public class TeamController{
    // 생성자 주입
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService){
        this.teamService = teamService;
    }

    @GetMapping("/ping")   
    public String ping(){
        return "pong";
    }
}