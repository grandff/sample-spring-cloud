package com.cloud.sample.boardservice.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {
    @GetMapping("/ping")
    public String ping(){
        return "pong";
    }
}
