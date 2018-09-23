package com.example.filterproject.namematcher.checker

import com.example.filterproject.namematcher.model.CheckResult
import com.example.filterproject.namematcher.model.RiskCustomer
import spock.lang.Specification

class DynamicCheckerImplTest extends Specification {

    private DynamicCheckerImpl dynamicChecker

    def setup() {
        dynamicChecker = new DynamicCheckerImpl()
    }

    def "CalculateCoefficient happy flow 1"() {

        given:
        RiskCustomer riskCustomer = RiskCustomer.builder()
                .firstName("Samuel")
                .lastName("Jackson")
                .email("email2@email.com")
                .address1("address1")
                .region_state("state")
                .zip("12234")
                .city("City")
                .country("US")
                .build()

        when:
        def result = dynamicChecker.calculateCoefficient(riskCustomer.getAttributeMap())

        then:
        assert result == 12.5
    }

    def "CalculateCoefficient happy flow 2"() {

        given:
        RiskCustomer riskCustomer = RiskCustomer.builder()
                .firstName("Samuel")
                .middleName("L")
                .lastName("Jackson")
                .email("email2@email.com")
                .address1("address1")
                .region_state("state")
                .zip("12234")
                .city("City")
                .country("US")
                .build()

        when:
        def result = dynamicChecker.calculateCoefficient(riskCustomer.getAttributeMap())

        then:
        assert result == 11.1
    }

    def "CheckNameGroup with different objects"() {
        given:
        RiskCustomer inputCustomer = RiskCustomer.builder()
                .firstName("Samuel")
                .middleName("L")
                .lastName("Jackson")
                .build()

        RiskCustomer foundCustomer = RiskCustomer.builder()
                .firstName("Antonio")
                .lastName("Jackson")
                .build()

        when:
        CheckResult result = dynamicChecker.apply(foundCustomer, inputCustomer)
        System.out.println(result)

        then:
        assert result.nameMatch < 50
    }

    def "CheckAddressGroup with different objects"() {
        given:
        RiskCustomer inputCustomer = RiskCustomer.builder()
                .address1("address2")
                .region_state("TX")
                .zip("12234")
                .city("City")
                .country("US")
                .build()

        RiskCustomer foundCustomer = RiskCustomer.builder()
                .address1("address1")
                .region_state("FL")
                .zip("12234")
                .city("City")
                .country("US")
                .build()

        when:
        CheckResult result = dynamicChecker.apply(foundCustomer, inputCustomer)
        System.out.println(result)

        then:
        assert result.nameMatch < 50
    }

    def "DynamicChecker with equals objects"() {
        given:
        RiskCustomer inputCustomer = RiskCustomer.builder()
                .firstName("Samuel")
                .middleName("L")
                .lastName("Jackson")
                .email("email2@email.com")
                .address1("address1")
                .region_state("state")
                .zip("12234")
                .city("City")
                .country("US")
                .build()

        RiskCustomer foundCustomer = RiskCustomer.builder()
                .firstName("Samuel")
                .middleName("L")
                .lastName("Jackson")
                .email("email2@email.com")
                .address1("address1")
                .region_state("state")
                .zip("12234")
                .city("City")
                .country("US")
                .build()

        when:
        CheckResult result = dynamicChecker.apply(foundCustomer, inputCustomer)

        then:
        assert result.addressMatch > 99
        assert result.nameMatch < 99
        assert result.totalMatch > 99
    }
}
