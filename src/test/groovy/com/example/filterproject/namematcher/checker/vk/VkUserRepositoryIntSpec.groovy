package com.example.filterproject.namematcher.checker.vk

import com.example.filterproject.namematcher.checker.BaseIntegrationTest
import com.example.filterproject.namematcher.integration.vk.model.VkUser
import com.example.filterproject.namematcher.integration.vk.repository.VkUserRepository
import org.springframework.beans.factory.annotation.Autowired

class VkUserRepositoryIntSpec extends BaseIntegrationTest {

    @Autowired
    private VkUserRepository vkUserRepository

    def setup() {
        vkUserRepository.deleteAll()
    }

    def "FindAll returns all users correctly"() {
        given:
        List<Integer> friends = new Integer[2]
        VkUser vkUser1 = VkUser.builder()
                .riskcustomerid(1)
                .possibleids([1000, 1001])
                .preferableid(1000)
                .totalfriendscount(2)
                .friends(friends)
                .build()
        VkUser vkUser2 = VkUser.builder()
                .riskcustomerid(2)
                .possibleids([8000, 8001])
                .preferableid(8000)
                .totalfriendscount(3)
                .friends(friends)
                .build()

        when:
        vkUserRepository.saveAll([vkUser1, vkUser2])

        then:
        List<VkUser> vkUsers = vkUserRepository.findAll()
        assert vkUsers.size() == 2
        VkUser user1 = vkUsers.get(0)
        VkUser user2 = vkUsers.get(1)
        assert user1.getId() == 1 && user1.getRiskcustomerid() == 1 && user1.getDateCreated() != null
        assert user2.getId() == 2 && user2.getRiskcustomerid() == 2 && user2.getDateCreated() != null
    }
}
