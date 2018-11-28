package com.example.filterproject.namematcher.integration.vk.service;

import com.vk.api.sdk.client.Lang;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.objects.users.UserFull;
import com.vk.api.sdk.objects.users.responses.SearchResponse;
import com.vk.api.sdk.queries.users.UsersSearchSex;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class VKUserService {

    //TODO MOVE ALL CONFIGS TO APPLICATION.YML
    private static final Integer vkUserId = 518453394;

    private static TransportClient transportClient = HttpTransportClient.getInstance();
    private static VkApiClient vk = new VkApiClient(transportClient);

    private static String token = new VkAuthenticator().getAccessToken().getAccess_token();
    private static UserActor userActor = new UserActor(vkUserId, token);

    private Integer resultLimit = 5;

    public List<UserFull> searchUser(String request, Integer city, Integer country) {
        log.info("[VK-USER-SERVICE] request received - {}, {}, {}", request, city, country);
        SearchResponse searchResponse = new SearchResponse();
        try {
            searchResponse = vk.users().search(userActor)
                    .q(request)
                    .city(city)
                    .country(country)
                    .sex(UsersSearchSex.MALE)
                    .lang(Lang.EN)
                    .count(resultLimit)
                    .execute();
        } catch (ClientException | ApiException e) {
            e.printStackTrace();
        }
        return searchResponse.getItems();
    }

    public List<UserFull> searchUser(String request, Integer country) {
        log.info("[VK-USER-SERVICE] request received - {}, {}, {}", request, country);
        SearchResponse searchResponse = new SearchResponse();
        try {
            searchResponse = vk.users().search(userActor)
                    .q(request)
                    .country(country)
                    .sex(UsersSearchSex.MALE)
                    .lang(Lang.EN)
                    .count(resultLimit)
                    .execute();
        } catch (ClientException | ApiException e) {
            e.printStackTrace();
        }
        return searchResponse.getItems();
    }

    public List<Integer> getUserFriendsIds(Integer userId) {
        GetResponse getResponse = new GetResponse();
        try {
            getResponse = vk.friends()
                    .get(userActor)
                    .userId(userId)
                    .lang(Lang.EN)
                    .execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return getResponse.getItems();
    }
}
