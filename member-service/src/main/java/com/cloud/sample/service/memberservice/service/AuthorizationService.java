package com.cloud.sample.service.memberservice.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AuthorizationService {

    // enum으로 권한을 따로 관리...? 일단 배열로 관리하기
    // 01이 사용 가능한 것과 02가 사용 가능한 url 나눔    
    final String[] ROLE_01_URL = {"/board/api/post/indiv/insert", "/board/api/post/indiv/view"};
    final String[] ROLE_02_URL = {"/board/api/post/team/insert", "/board/api/post/team/view"};
    
    // 권한 인가 여부 확인
    // gateway에서 호출함
    public Boolean isAuthorization(List<String> roles, String httpMethod, String requestPath){
        System.out.println("roles :: " + roles.get(0).toString());
        System.out.println("httpMethod :: " + httpMethod);
        System.out.println("requestPath :: " + requestPath);

        if(roles.size() == 0){
            System.out.println("권한 목록 비어있음");
            return false;
        }

        return isContainMatch(roles.get(0).toString(), httpMethod, requestPath);
    }

    // 인가여부 체크
    private Boolean isContainMatch(String roleId, String httpMethod, String requestPath){
        // requestPath에서 권한에 맞는 url이 들어온건지 확인
        // 권한에 맞지 않은 url이면 false 리턴
        Boolean result = false;  

        // 뭔가 이상하지만..
        if((!Arrays.stream(ROLE_01_URL).anyMatch(str -> str.equals(requestPath))) && (!Arrays.stream(ROLE_02_URL).anyMatch(str -> str.equals(requestPath)))){
            result = true;
        }else{
            if(roleId.equals("01")){
                result = Arrays.stream(ROLE_01_URL).anyMatch(str -> str.equals(requestPath));
            }else if(roleId.equals("02")){
                result = Arrays.stream(ROLE_02_URL).anyMatch(str -> str.equals(requestPath));
            }
        }        
        System.out.println("authorization result :: " + result);
                
        return result;
    }
}
