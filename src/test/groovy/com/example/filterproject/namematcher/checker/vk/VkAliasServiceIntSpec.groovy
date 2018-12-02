package com.example.filterproject.namematcher.checker.vk

import com.example.filterproject.namematcher.checker.BaseIntegrationTest
import com.example.filterproject.namematcher.integration.vk.service.VkAliasService
import com.vk.api.sdk.objects.users.UserFull
import org.springframework.beans.factory.annotation.Autowired

class VkAliasServiceIntSpec extends BaseIntegrationTest {

    @Autowired
    private VkAliasService vkAliasService

    def "Should return correct response"() {
        when:
        List<UserFull> foundUsers = vkAliasService.findUser("Valery", "Kuznetsov", "UA")

        then:
        assert !foundUsers.isEmpty()
    }
}
