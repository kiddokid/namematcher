package com.example.filterproject.namematcher.integration.vk.service;

import com.vk.api.sdk.objects.users.UserFull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VkAliasService {

    private VkApplicationService vkApplicationService;
    private VKUserService vkUserService;

    public VkAliasService(VkApplicationService vkApplicationService,
                          VKUserService vkUserService) {
        this.vkApplicationService = vkApplicationService;
        this.vkUserService = vkUserService;
    }

    public void process() {
//        List<UserFull> user = findUser("Igor", "Usevich", "UA");
//
//        System.out.println(" FIND USER - " + user.get(0).getFirstName() + " " + user.get(0).getLastName());
//        System.out.println(" Friends COUNT - " + vkUserService.getUserFriendsIds(user.get(0).getId()));
        isUserHitOnline("Andriy", "Shmatko", "UA");
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

    public boolean isUserHitOnline(String firstName, String lastName, String country) {
        List<Integer> totalFriendsList = new ArrayList<>();
        List<UserFull> foundUsers = findUser(firstName, lastName, country);
        foundUsers.forEach(userFull -> totalFriendsList.addAll(vkUserService.getUserFriendsIds(userFull.getId())));
        log.info("[VK-ALIAS-SERVICE] {} total friends found for possible match user", totalFriendsList.size());
        return true;
    }

    public boolean isUserHitOnline(String firstName, String lastName, String country, String city) {
        List<Integer> totalFriendsList = new ArrayList<>();
        List<UserFull> foundUsers = findUser(firstName, lastName, country, city);
        foundUsers.forEach(userFull -> totalFriendsList.addAll(vkUserService.getUserFriendsIds(userFull.getId())));
        log.info("[VK-ALIAS-SERVICE] {} total friends found for possible match user", totalFriendsList.size());
        return true;
    }
}
