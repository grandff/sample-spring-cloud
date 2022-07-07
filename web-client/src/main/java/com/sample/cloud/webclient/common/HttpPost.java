package com.sample.cloud.webclient.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

@Service
public class HttpPost {
    public String RestAPICall(String sendUrl, String jsonValue, HttpServletRequest request) throws IOException{
        String inputLine;
        StringBuffer outResult = new StringBuffer();

        URL url = new URL(sendUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");  // 요청내용 확인
        conn.setRequestProperty("Content-Type", "application/json; utf-8");
        conn.setRequestProperty("Accept", "application/json");
        //conn.setConnectTimeout(10000);    // 필요시 사용
	    //conn.setReadTimeout(10000);       // 필요시 사용

        // header 세팅
        Enumeration<String> headerEnum = request.getHeaderNames();
        while(headerEnum.hasMoreElements()){
            String key = (String) headerEnum.nextElement();
            String value = request.getHeader(key);
            conn.setRequestProperty(key, value);
        }
        
        OutputStream out = conn.getOutputStream();
        out.write(jsonValue.getBytes("UTF-8"));
        out.flush();

        // 리턴 결과 확인
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        while((inputLine = in.readLine()) != null){
            outResult.append(inputLine);
        }

        conn.disconnect();        

        return outResult.toString();
    }
}
