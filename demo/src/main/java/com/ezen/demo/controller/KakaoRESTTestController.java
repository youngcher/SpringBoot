package com.ezen.demo.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/msg/rest")
public class KakaoRESTTestController 
{
	@GetMapping("")
	public String index()
	{
		return "thymeleaf/kakao_restapi_test";
	}

	@GetMapping("/login/oauth")
	@ResponseBody
	public Map<String,String> auth_kakao_callback(
			@RequestParam(value="code", required=false) String code
			, Model model) throws Exception 
	{
		log.debug("code : {}", code);
		
		/*인가 코드로 토큰 발급을 요청합니다. 인가 코드 받기만으로는 카카오 로그인이 완료되지 않으며, 
         * 토큰 받기까지 마쳐야 카카오 로그인을 정상적으로 완료할 수 있다.*/
        
		// 인증서버에서 리턴한 code 를 사용하여 다시 사용권한(access_token)을 요청한다
        String access_token = getAccessToken(code);
        log.debug("access_Token : {}", access_token);

        HashMap<String, String> userInfo = getUserInfo(access_token);
        log.debug("access_Token : {}", access_token);
        log.debug("userInfo : {}", userInfo.get("email"));
        log.debug("nickname : {}", userInfo.get("nickname"));
       
        JSONObject kakaoInfo =  new JSONObject(userInfo);
        model.addAttribute("kakaoInfo", kakaoInfo);
        
        sendMsg(access_token);
        
        return null; //본인 원하는 경로 설정
	}
	
	//토큰발급
	public String getAccessToken (String authorize_code) {
        String access_token = null;
        String refresh_token = null;
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // URL연결은 입출력에 사용 될 수 있고, POST 혹은 PUT 요청을 하려면 setDoOutput을 true로 설정해야함.
            // 필수 파라미터를 포함하여 POST로 요청
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //	POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=");
            sb.append("&redirect_uri=http://localhost/msg/rest/login/oauth");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            log.debug("responseCode : {}", responseCode);

            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            log.debug("response body : {}", result);

            //json-simple 라이브러리 사용
            JSONObject jsObj = null;
            JSONParser jsParser = new JSONParser();
            try {
				jsObj = (JSONObject)jsParser.parse(result.trim());
			} catch (ParseException e) {
				e.printStackTrace();
			}
            
            access_token = (String)jsObj.get("access_token");
            refresh_token = (String)jsObj.get("refresh_token");
            
            log.debug("access_token : {}", access_token);
            log.debug("refresh_token : {}", refresh_token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return access_token;
	}
		
	//유저정보조회
    public HashMap<String, String> getUserInfo (String access_Token) 
    {
        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, String> userInfo = new HashMap<>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            log.debug("responseCode : {}", responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            log.debug("response body : {}", result);
            //{"id":2363405993,"connected_at":"2022-07-28T07:31:11Z","properties":{"nickname":"源?李쎌슫"},"kakao_account":{"profile_nickname_needs_agreement":false,"profile":{"nickname":"源?李쎌슫"},"has_email":true,"email_needs_agreement":false,"is_email_valid":true,"is_email_verified":true,"email":"cwiskykim@gmail.com"}}
            
            JSONParser parser = new JSONParser();
            JSONObject jsObj = null;
            String nickname = null;
            String email = null; 
            try {
				jsObj = (JSONObject)parser.parse(result);
				JSONObject propObj = (JSONObject)jsObj.get("properties");
				nickname = (String) propObj.get("nickname");
				
				JSONObject account = (JSONObject)jsObj.get("kakao_account");
				email = (String) account.get("email");
			} catch (ParseException e) {
				e.printStackTrace();
			}
            
            userInfo.put("accessToken", access_Token);
            userInfo.put("nickname", nickname);
            userInfo.put("email", email);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userInfo;
    }
    
    public void sendMsg(String access_token)
    {
    	String reqURL = "https://kapi.kakao.com/v2/api/talk/memo/default/send";
    	try {
	    	URL url = new URL(reqURL);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestMethod("POST");
	        conn.setDoOutput(true);
	        conn.setDoInput(true);

            conn.setUseCaches(false);
            
	        //    요청에 필요한 Header에 포함될 내용
	        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        conn.setRequestProperty("Cache-Control", "no-cache");
	        //conn.setRequestProperty("Content-Type", "application/json"); // 요청 데이터 타입
	        //conn.setRequestProperty("Accept", "application/json");       // 응답 데이터 타입
	        conn.setRequestProperty("Authorization", "Bearer " + access_token);
	
	        //POST 요청 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            JSONObject jsObj = new JSONObject();
            jsObj.put("object_type", "text");
            jsObj.put("text", "텍스트 영역입니다. 최대 200자 표시 가능합니다.");
            jsObj.put("button_title", "바로 확인");
            
            JSONObject linkObj = new JSONObject();
            linkObj.put("web_url", "https://developers.kakao.com");
            linkObj.put("mobile_web_url", "https://developers.kakao.com");
            
            jsObj.put("link", linkObj);
            
            String dataString = "template_object=" + jsObj.toJSONString();
            log.debug("dataObj : {}", dataString);

            bw.write(dataString);
            bw.flush();
	        
	        int responseCode = conn.getResponseCode();
	        log.debug("SEND, responseCode : {}", responseCode);
	        
	        InputStream stream = conn.getErrorStream();
	        String errRes = "";
            if (stream != null) {
                try (Scanner scanner = new Scanner(stream)) {
                    scanner.useDelimiter("\\Z");
                    errRes = scanner.next();
                }            
                System.out.println("error response : " + errRes);
            }
	        
	        try(BufferedReader br = new BufferedReader(
	        		  new InputStreamReader(conn.getInputStream(), "utf-8"))) 
	        {
    		    StringBuilder response = new StringBuilder();
    		    String responseLine = null;
    		    while ((responseLine = br.readLine()) != null) {
    		        response.append(responseLine.trim());
    		    }
    		    log.debug("최종 응답 : {}", response.toString());
    		}
    	}catch(Exception ex) {
    		ex.printStackTrace();
    	}
    }
}