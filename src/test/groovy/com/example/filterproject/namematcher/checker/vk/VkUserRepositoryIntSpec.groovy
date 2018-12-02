package com.example.filterproject.namematcher.checker.vk

import com.example.filterproject.namematcher.checker.BaseIntegrationTest
import com.example.filterproject.namematcher.integration.vk.model.VkUser
import com.example.filterproject.namematcher.integration.vk.repository.VkUserRepository
import org.springframework.beans.factory.annotation.Autowired

class VkUserRepositoryIntSpec extends BaseIntegrationTest {

    @Autowired
    private VkUserRepository vkUserRepository

    def "FindAll returns all users correctly"() {
        given:
        VkUser vkUser1 = VkUser.builder()
                .riskcustomerid(1)
                .possibleids(1000, 1001)
                .preferableid(1000)
                .totalfriendscount(2)
                .friends(2000L, 2001L)
                .build()
        VkUser vkUser2 = VkUser.builder()
                .riskcustomerid(2)
                .possibleids(8000, 8001)
                .preferableid(8000)
                .totalfriendscount(3)
                .friends(4000L, 4001L, 4002L)
                .build()

        when:
        vkUserRepository.saveAll([vkUser1, vkUser2])
        vkUserRepository.flush()

        then:
        List<VkUser> vkUsers = vkUserRepository.findAll()
        assert vkUsers.size() == 2
        VkUser user1 = vkUsers.get(0)
        VkUser user2 = vkUsers.get(1)
        assert user1.getId() == 1 && user1.getRiskcustomerid() == 1 && user1.getDateCreated() != null
        assert user2.getId() == 2 && user2.getRiskcustomerid() == 2 && user2.getDateCreated() != null
    }
}
