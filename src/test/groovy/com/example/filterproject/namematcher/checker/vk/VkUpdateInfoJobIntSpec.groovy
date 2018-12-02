package com.example.filterproject.namematcher.checker.vk

import com.example.filterproject.namematcher.checker.BaseIntegrationTest
import com.example.filterproject.namematcher.dao.RiskCustomerRepository
import com.example.filterproject.namematcher.integration.vk.model.VkUser
import com.example.filterproject.namematcher.integration.vk.repository.VkUserRepository
import com.example.filterproject.namematcher.job.VkUserSaveJob
import com.example.filterproject.namematcher.model.RiskCustomer
import org.springframework.beans.factory.annotation.Autowired

class VkUpdateInfoJobIntSpec extends BaseIntegrationTest {

    @Autowired
    private VkUserRepository vkUserRepository

    @Autowired
    private RiskCustomerRepository riskCustomerRepository

    @Autowired
    private VkUserSaveJob vkUserSaveJob

    def setup() {
        vkUserRepository?.deleteAllInBatch()
        riskCustomerRepository?.deleteAllInBatch()
    }

    def "Migration of VK data works correctly"() {
        given:
        RiskCustomer customer1 = RiskCustomer.builder()
                .firstName("Anton")
                .lastName("Antipov")
                .email("email2@email.com")
                .address1("address1")
                .region_state("state")
                .zip("12234")
                .city("Odessa")
                .country("UA")
                .reason("1")
                .build()

        RiskCustomer customer2 = RiskCustomer.builder()
                .firstName("Samuel")
                .middleName("L")
                .lastName("Jackson")
                .email("email2@email.com")
                .address1("address1")
                .region_state("state")
                .zip("12234")
                .city("City")
                .country("US")
                .reason("1")
                .build()

        RiskCustomer customer3 = RiskCustomer.builder()
                .firstName("Dmitriy")
                .lastName("Medvedev")
                .email("email2@email.com")
                .address1("address1")
                .region_state("state")
                .zip("12234")
                .city("Moscow")
                .country("RU")
                .reason("1")
                .build()

        riskCustomerRepository.saveAll([customer1, customer2, customer3])

        when:
        vkUserSaveJob.updateVkInfo()

        then:
        List<VkUser> vkUsers = vkUserRepository.findAll()
        System.out.println(vkUsers)
        assert vkUsers.size() == 2
    }
}
