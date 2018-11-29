package com.example.filterproject.namematcher.integration.vk.service;

import com.example.filterproject.namematcher.integration.vk.model.VkUserEntity;
import com.vk.api.sdk.objects.users.UserFull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VkAliasService {

    private VkApplicationService vkApplicationService;
    private VKUserService vkUserService;

    public VkAliasService(VkApplicationService vkApplicationService,
                          VKUserService vkUserService) {
        this.vkApplicationService = vkApplicationService;
        this.vkUserService = vkUserService;
    }

    public void process() {
        List<UserFull> user = findUser("Igor", "Usevich", "UA");

        System.out.println(" FIND USER - " + user.get(0).getFirstName() + " " + user.get(0).getLastName());
        System.out.println(" Friends COUNT - " + vkUserService.getUserFriendsIds(user.get(0).getId()));
    }

    public List<UserFull> findUser(String firstName, String lastName, String country, String city) {
        Integer countryId = vkApplicationService.getCountryIdSafely(country);
        Integer cityId = vkApplicationService.getCityIdSafely(city, countryId);
        return vkUserService.searchUser(firstName+ " " + lastName, cityId, countryId);
    }

    public List<UserFull> findUser(String firstName, String lastName, String country) {
        Integer countryId = vkApplicationService.getCountryIdSafely(country);
        return vkUserService.searchUser(firstName+ " " + lastName, countryId);
    }
}
