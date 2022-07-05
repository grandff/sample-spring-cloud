package com.sample.cloud.webclient.main;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    // 메인화면 이동
    @GetMapping(value = "/")
    public String goHome(HttpServletRequest request){
        return "content/home";
    }
}
