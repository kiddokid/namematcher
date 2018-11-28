package com.example.filterproject.namematcher.integration.vk.service;

import com.example.filterproject.namematcher.integration.vk.model.VkOauthLoginResponse;
import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class VkAuthenticator {

    private static RestTemplate restTemplate = new RestTemplate();

    //TODO MOVE TO APPLICATION YML
    private static Long CLIENT_ID = 3140623L;
    private static String CLIENT_SECRET = "VeWdmVclDCtn6ihuP1nt";
    private static String USERNAME = "37257851403";
    private static String PASSWORD = "3911673q";


    public VkOauthLoginResponse getAccessToken() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://oauth.vk.com/token?grant_type=password&client_id=" + CLIENT_ID.toString()
                + "&client_secret=" + CLIENT_SECRET
                + "&username=" + USERNAME
                + "&password=" + PASSWORD
                + "&scope=friends", String.class);
        Gson gson = new Gson();
        VkOauthLoginResponse response =  gson.fromJson(responseEntity.getBody(), VkOauthLoginResponse.class);
        return response;
    }
}
