package com.example.filterproject.namematcher.tester;

import com.example.filterproject.namematcher.integration.vk.service.VkAuthenticator;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.queries.users.UsersSearchSex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VkTest {

    private static final String CLIENT_SECRET = "PIiFfsQ9qVRmQ5cZXgZj";
    private static final Integer APP_ID = 6762663;
    private static final String ACCESS_TOKEN = "6ba7635c6ba7635c6ba7635c0c6bc053fb66ba76ba7635c305dd4fa5f9edd53822283d7";

    private static TransportClient transportClient = HttpTransportClient.getInstance();
    private static VkApiClient vk = new VkApiClient(transportClient);

//    private static String token = new VkAuthenticator().getAccessToken().getAccess_token();
//
//    @Autowired
//    private VkAuthenticator vkAuthenticator;
//
//    public void tr() throws ClientException, ApiException {
//        UserActor userActor = new UserActor(518453394, token);
//        String res = vk.users().search(userActor)
//                .q("Smolov")
//                .city(1)
//                .sex(UsersSearchSex.MALE)
//                .count(20)
//                .executeAsString();
//
//        System.out.println(res);
//    }
}
