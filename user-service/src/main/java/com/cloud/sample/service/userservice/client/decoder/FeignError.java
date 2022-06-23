package com.cloud.sample.service.userservice.client.decoder;

import feign.codec.ErrorDecoder;
import feign.Response;

// feign client error 처리
public class FeignError implements ErrorDecoder{
    @Override
    public Exception decode(String methodKey, Response response){
        switch (response.status()) {
            case 404:
                if(methodKey.contains("getOrders")){
                    return new Exception("해당 사용자는 팀에 가입되어 있지 않습니다.");                    
                }                                
            
            default:
                return new Exception("오류 발생");
        }        
    }
}