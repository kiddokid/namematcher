package com.example.filterproject.namematcher.integration.vk.service;

import com.example.filterproject.namematcher.dao.SystemPropertyRepository;
import com.example.filterproject.namematcher.integration.vk.model.VkOauthLoginResponse;
import com.example.filterproject.namematcher.model.SystemProperty;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
@Slf4j
public class VkAuthenticator {

    private static RestTemplate restTemplate = new RestTemplate();
    //TODO MOVE TO APPLICATION YML
    private static Long CLIENT_ID = 3140623L;
    private static String CLIENT_SECRET = "VeWdmVclDCtn6ihuP1nt";
    private static String USERNAME = "37257851403";
    private static String PASSWORD = "3911673q";

    private SystemPropertyRepository systemPropertyRepository;

    public VkAuthenticator(SystemPropertyRepository systemPropertyRepository) {
        this.systemPropertyRepository = systemPropertyRepository;
    }

    public String getAccessToken() {
        String token = getAccessTokenFromDB();
        if (Objects.nonNull(token)) {
            if (isAccessTokenWorks(token)) {
                log.info("[VK-AUTHENTICATOR] - working token already exists. Returning...");
                return token;
            }
        }
        log.info("[VK-AUTHENTICATOR] - Requesting new User Access Token ...");
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://oauth.vk.com/token?grant_type=password&client_id=" + CLIENT_ID.toString()
                + "&client_secret=" + CLIENT_SECRET
                + "&username=" + USERNAME
                + "&password=" + PASSWORD
                + "&scope=friends", String.class);
        Gson gson = new Gson();
        VkOauthLoginResponse response = gson.fromJson(responseEntity.getBody(), VkOauthLoginResponse.class);
        if (Objects.nonNull(response)) {
            log.info("[VK-AUTHENTICATOR] - saving access token to DB...");
            SystemProperty systemProperty = systemPropertyRepository.saveAndFlush(SystemProperty.builder()
                    .property("vkAccessToken")
                    .value(response.getAccess_token())
                    .build());
            log.info("[VK-AUTHENTICATOR] - saved token with property name {}", systemProperty.getProperty());
        }
        return response.getAccess_token();
    }

    private String getAccessTokenFromDB() {
        return systemPropertyRepository.findById("vkAccessToken")
                .map(SystemProperty::getValue)
                .orElse(null);
    }

    private boolean isAccessTokenWorks(String token) {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://api.vk.com/method/friends.areFriends?user_ids=1&access_token=" + token, String.class);
        return !responseEntity.getBody().contains("error");
    }
}
