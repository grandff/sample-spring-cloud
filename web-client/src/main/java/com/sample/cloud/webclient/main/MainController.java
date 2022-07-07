package com.sample.cloud.webclient.main;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    // 메인화면 이동
    @GetMapping(value = "/")
    public ModelAndView goHome(HttpServletRequest request){
        ModelAndView mav = new ModelAndView();

        List<String> utilList = new ArrayList<String>();
        utilList.add("회원로그인");
        utilList.add("회원가입");
        utilList.add("회원수정");
        utilList.add("로그아웃");
        utilList.add("게시글 CRUD");
        
        // mav에 담아서 리턴
        mav.addObject("utilList", utilList);
        mav.setViewName("index");   // page name

        return mav;
    }
}
