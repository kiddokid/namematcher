package com.example.filterproject.namematcher.checker.repository

import com.example.filterproject.namematcher.checker.BaseIntegrationTest
import com.example.filterproject.namematcher.dao.RiskCustomerRepository
import com.example.filterproject.namematcher.model.RiskCustomer
import org.springframework.beans.factory.annotation.Autowired

class RiskCustomerRepositoryIntSpec extends BaseIntegrationTest {

    @Autowired
    private RiskCustomerRepository riskCustomerRepository

    def "finAll works correctly"() {
        RiskCustomer customer1 = RiskCustomer.builder()
                .firstName("Samuel")
                .middleName("L")
                .lastName("Jackson")
                .email("email2@email.com")
                .address1("address1")
                .region_state("state")
                .zip("12234")
                .city("City")
                .country("US")
                .reason("some reason")
                .build()

        when:
        riskCustomerRepository.save(customer1)

        then:
        List<RiskCustomer> riskCustomerList = riskCustomerRepository.findAll()
        assert riskCustomerList.size() == 1
    }
}
