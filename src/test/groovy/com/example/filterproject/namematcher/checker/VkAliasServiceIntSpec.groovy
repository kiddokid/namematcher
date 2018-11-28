package com.example.filterproject.namematcher.checker

import com.example.filterproject.namematcher.integration.vk.service.VkAliasService
import com.vk.api.sdk.objects.users.UserFull
import org.springframework.beans.factory.annotation.Autowired

class VkAliasServiceIntSpec extends BaseIntegrationTest {

    @Autowired
    private VkAliasService vkAliasService

    def "Should return correct response"() {
        when:
        List<UserFull> foundUsers = vkAliasService.findUser("Igor", "Usevich", "UA")

        then:
        assert !foundUsers.isEmpty()
    }
}
