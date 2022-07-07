package com.sample.cloud.webclient.member;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "member")
public class MemberController {
    @GetMapping(value = "join")
    public String joinPage(){
        return "member/join";
    }
}
