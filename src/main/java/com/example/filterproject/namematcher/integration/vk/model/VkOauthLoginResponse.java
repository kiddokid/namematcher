package com.example.filterproject.namematcher.integration.vk.model;

import lombok.Data;

@Data
public class VkOauthLoginResponse {

    private String access_token;
    private Long expires_in;
    private Long user_id;
}
