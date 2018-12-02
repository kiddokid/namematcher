package com.example.filterproject.namematcher.job;

import com.example.filterproject.namematcher.dao.RiskCustomerRepository;
import com.example.filterproject.namematcher.integration.vk.model.VkUser;
import com.example.filterproject.namematcher.integration.vk.repository.VkUserRepository;
import com.example.filterproject.namematcher.integration.vk.service.VKUserService;
import com.example.filterproject.namematcher.integration.vk.service.VkAliasService;
import com.example.filterproject.namematcher.model.RiskCustomer;
import com.vk.api.sdk.objects.users.UserFull;
import com.vk.api.sdk.objects.users.UserMin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class VkUserSaveJob {

    private RiskCustomerRepository riskCustomerRepository;
    private VkUserRepository vkUserRepository;
    private VkAliasService vkAliasService;
    private VKUserService vkUserService;

    public VkUserSaveJob(RiskCustomerRepository riskCustomerRepository,
                         VkAliasService vkAliasService,
                         VKUserService vkUserService,
                         VkUserRepository vkUserRepository) {
        this.riskCustomerRepository = riskCustomerRepository;
        this.vkAliasService = vkAliasService;
        this.vkUserService = vkUserService;
        this.vkUserRepository = vkUserRepository;
    }

    @Scheduled(fixedDelay = 20000)
    public void updateVkInfo() {
        List<RiskCustomer> riskCustomerList = riskCustomerRepository.getEastEuropeanCustomersWithoutVk();
        log.info("[VK-USER-SAVE-JOB] - found {} users to update VK info", riskCustomerList.size());
        riskCustomerList.forEach(riskCustomer -> {
            List<Integer> totalFriends = new ArrayList<>();
            List<UserFull> foundUsers = vkAliasService.findUser(riskCustomer.getFirstName(), riskCustomer.getLastName(), riskCustomer.getCountry(), riskCustomer.getCity());
            if (foundUsers.size() > 0) {
                foundUsers.forEach(userFull -> {
                    totalFriends.addAll(vkUserService.getUserFriendsIds(userFull.getId()));
                });
                Long[] possibleIds = foundUsers.stream().map(UserMin::getId).map(Long::valueOf).toArray(Long[]::new);
                Long[] friends = totalFriends.stream().map(Long::valueOf).toArray(Long[]::new);
                VkUser vkUser = VkUser.builder()
                        .riskcustomerid(riskCustomer.getId())
                        .possibleids(possibleIds.length > 0 ? possibleIds : null)
                        .preferableid(possibleIds.length > 0 ? possibleIds[0] : null)
                        .totalfriendscount(friends.length)
                        .friends(friends)
                        .build();
                log.info("[VK-USER-SAVE-JOB] - trying to save info for user {}", riskCustomer.getId());
                vkUserRepository.save(vkUser);
                vkUserRepository.flush();
            }
        });
    }
}
