package com.example.filterproject.namematcher.integration.vk.service;

import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.ServiceClientCredentialsFlowResponse;
import com.vk.api.sdk.objects.database.responses.GetCitiesResponse;
import com.vk.api.sdk.objects.database.responses.GetCountriesResponse;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Service
public class VkApplicationService {

    private static final Integer APP_ID = 6762663;
    private static final String CLIENT_SECRET = "PIiFfsQ9qVRmQ5cZXgZj";

    private static TransportClient transportClient = HttpTransportClient.getInstance();
    private static VkApiClient vkApp = new VkApiClient(transportClient);

    private ServiceActor actor;

    public VkApplicationService() {
        ServiceClientCredentialsFlowResponse authResponse = null;
        try {
            authResponse = vkApp.oauth()
                    .serviceClientCredentialsFlow(APP_ID, CLIENT_SECRET)
                    .execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        if (nonNull(authResponse)) {
            actor = new ServiceActor(APP_ID, authResponse.getAccessToken());
        }
    }

    public void process() {
        System.out.println("CITY ID SAFELY - "+getCityIdSafely("Lviv","UA"));
    }

    public Integer getCountryIdSafely(String country) {
        Integer result = null;
        GetCountriesResponse getCountriesResponse = new GetCountriesResponse();
        try {
             getCountriesResponse = vkApp.database().getCountries(actor)
                    .code(country)
                    .count(1)
                    .execute();
        } catch (ClientException | ApiException e) {
            e.printStackTrace();
        }
        if (nonNull(getCountriesResponse.getItems())) {
            result = getCountriesResponse.getItems().get(0).getId();
        }
        return result;
    }

    public Integer getCityIdSafely(String city, String country) {
        Integer result = null;
        GetCitiesResponse getCitiesResponse = new GetCitiesResponse();
        try {
            vkApp.database().getCities(actor, getCountryIdSafely(country))
                    .q(city)
                    .count(1)
                    .execute();
        } catch (ClientException | ApiException e) {
            e.printStackTrace();
        }
        if (nonNull(getCitiesResponse.getItems()))
            result = getCitiesResponse.getItems().get(0).getId();
        return result;
    }

    public Integer getCityIdSafely(String city, Integer countryId) {
        Integer result = null;
        GetCitiesResponse getCitiesResponse = new GetCitiesResponse();
        try {
            getCitiesResponse = vkApp.database().getCities(actor,countryId)
                    .q(city)
                    .count(1)
                    .execute();
        } catch (ClientException | ApiException e) {
            e.printStackTrace();
        }
        if (nonNull(getCitiesResponse.getItems()) && getCitiesResponse.getItems().size() > 0 )
            result = getCitiesResponse.getItems().get(0).getId();
        return result;
    }
}
