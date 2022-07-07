package com.sample.cloud.webclient.member;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sample.cloud.webclient.common.HttpPost;
import com.sample.cloud.webclient.member.model.MemberVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "member")
public class MemberController {

    private HttpPost httpPost;

    @GetMapping(value = "join")
    public String joinPage(){
        return "member/join";
    }

    @PostMapping(value="join")
    public ModelAndView joinAction(@RequestBody MemberVO memberVO, HttpServletRequest request) throws IOException{
        ModelAndView mav = new ModelAndView();        
        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("userId", memberVO.getUserId());
        resultMap.put("userName", memberVO.getUserName());
        resultMap.put("password", memberVO.getPassword());
        resultMap.put("tel", memberVO.getTel());
        resultMap.put("email", memberVO.getEmail1() + "@" + memberVO.getEmail2());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(resultMap);
        String result = httpPost.RestAPICall("http://localhost:8000/member/api/join", json, request);

        mav.addObject("result", result);
        //mav.setViewName("member/result");
        
        return mav;
    }
}
