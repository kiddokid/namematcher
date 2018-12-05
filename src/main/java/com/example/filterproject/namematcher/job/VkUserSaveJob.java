package com.example.filterproject.namematcher.job;

import com.example.filterproject.namematcher.dao.RiskCustomerRepository;
import com.example.filterproject.namematcher.dao.SystemPropertyRepository;
import com.example.filterproject.namematcher.integration.vk.model.VkUser;
import com.example.filterproject.namematcher.integration.vk.repository.VkUserRepository;
import com.example.filterproject.namematcher.integration.vk.service.VKUserService;
import com.example.filterproject.namematcher.integration.vk.service.VkAliasService;
import com.example.filterproject.namematcher.model.RiskCustomer;
import com.example.filterproject.namematcher.model.SystemProperty;
import com.vk.api.sdk.objects.users.UserFull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "job.integration.vk.save", name="enabled", havingValue="true", matchIfMissing = true)
public class VkUserSaveJob {

    private RiskCustomerRepository riskCustomerRepository;
    private VkUserRepository vkUserRepository;
    private VkAliasService vkAliasService;
    private VKUserService vkUserService;
    private SystemPropertyRepository systemPropertyRepository;

    public VkUserSaveJob(RiskCustomerRepository riskCustomerRepository,
                         VkAliasService vkAliasService,
                         VKUserService vkUserService,
                         VkUserRepository vkUserRepository,
                         SystemPropertyRepository systemPropertyRepository) {
        this.riskCustomerRepository = riskCustomerRepository;
        this.vkAliasService = vkAliasService;
        this.vkUserService = vkUserService;
        this.vkUserRepository = vkUserRepository;
        this.systemPropertyRepository = systemPropertyRepository;
    }

    @Scheduled(fixedDelay = 120000)
    public void updateVkInfo() {
        vkUserService.init();
        Integer offset = Integer.valueOf(getOffset());
        List<RiskCustomer> riskCustomerList = riskCustomerRepository.getEastEuropeanCustomersWithoutVk(offset);
        log.info("[VK-USER-SAVE-JOB] - found {} users to update VK info", riskCustomerList.size());
        riskCustomerList.forEach(riskCustomer -> {
            ArrayList<Integer> totalFriends = new ArrayList<>();
            List<UserFull> foundUsers = vkAliasService.findUser(riskCustomer.getFirstName(), riskCustomer.getLastName(), riskCustomer.getCountry(), riskCustomer.getCity());
            if (foundUsers.size() > 0) {
                foundUsers.forEach(userFull -> {
                    totalFriends.addAll(vkUserService.getUserFriendsIds(userFull.getId()));
                });
                ArrayList<Integer> possibleids = foundUsers.stream().map(UserFull::getId).collect(Collectors.toCollection(ArrayList::new));
                VkUser vkUser = VkUser.builder()
                        .riskcustomerid(riskCustomer.getId())
                        .possibleids(possibleids)
                        .preferableid(possibleids.size() > 0 ? possibleids.get(0) : null)
                        .totalfriendscount(totalFriends.size())
                        .friends(totalFriends)
                        .build();
                log.info("[VK-USER-SAVE-JOB] - trying to save info for user {}", riskCustomer.getId());
                vkUserRepository.save(vkUser);
                vkUserRepository.flush();
            }
        });
        riskCustomerList.stream()
                .max(Comparator.comparing(RiskCustomer::getId)).map(RiskCustomer::getId)
                .ifPresent(id -> {
                    updateOffset(String.valueOf(id));
                });
    }

    private String getOffset() {
        SystemProperty systemProperty = systemPropertyRepository.findById("vkMigrationOffset").orElse(null);
        return Objects.nonNull(systemProperty) ? systemProperty.getValue() : "0";
    }

    private void updateOffset(String offset) {
        systemPropertyRepository.save(SystemProperty.builder()
                .property("vkMigrationOffset")
                .value(offset)
                .build());
    }
}
