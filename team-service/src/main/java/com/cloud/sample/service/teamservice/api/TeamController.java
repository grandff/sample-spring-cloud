package com.cloud.sample.service.teamservice.api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloud.sample.service.teamservice.service.TeamService;

import com.cloud.sample.service.teamservice.api.dto.TeamResponseData;
import com.cloud.sample.service.teamservice.api.dto.TeamMemberAddData;
import com.cloud.sample.service.teamservice.api.dto.TeamCreateData;

import org.springframework.http.ResponseEntity;
@RestController
public class TeamController{
    // 생성자 주입
    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService){
        this.teamService = teamService;
    }

    // 통신 확인
    @GetMapping("/ping")   
    public String ping(){
        return "pong";
    }

    @PostMapping("/teams")
    public TeamResponseData create(@RequestBody TeamCreateData teamCreateData){
        return teamService.save(teamCreateData);
    }

    @PostMapping("/{userId}/teams")
    public ResponseEntity addTeamMember(@PathVariable("userId") Long id,
                                        @RequestBody TeamMemberAddData requestData){        
        teamService.addTeamMember(id, requestData.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/teams")
    public TeamResponseData getTeamByUserId(@PathVariable("userId") Long userId){
        return teamService.getTeamByUserId(userId);
    }
}