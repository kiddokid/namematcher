package com.example.filterproject.namematcher.checker

import com.example.filterproject.namematcher.checker.implementations.NGramSimilarityCheckerImpl
import com.example.filterproject.namematcher.model.CheckResult
import com.example.filterproject.namematcher.model.NormilizedCustomerData
import com.example.filterproject.namematcher.model.RiskCustomer
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class NGramSimilarityCheckerTest extends Specification{

    private NGramSimilarityCheckerImpl dynamicChecker

    def setup() {
        dynamicChecker = new NGramSimilarityCheckerImpl()
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
        NormilizedCustomerData inputCustomer = NormilizedCustomerData.builder()
                .name("samueljackson")
                .build()

        NormilizedCustomerData foundCustomer = NormilizedCustomerData.builder()
                .name("antoniojackson")
                .build()

        when:
        CheckResult result = dynamicChecker.calculate(foundCustomer, inputCustomer)
        System.out.println(result)

        then:
        assert result.nameMatch < 65
    }

    def "CheckAddressGroup with different but similar objects"() {
        given:
        NormilizedCustomerData inputCustomer = NormilizedCustomerData.builder()
                .address1("address2")
                .region("TX")
                .zip("12234")
                .city("City")
                .country("US")
                .build()

        NormilizedCustomerData foundCustomer = NormilizedCustomerData.builder()
                .address1("address1")
                .address2("address2")
                .region("FL")
                .zip("12234")
                .city("City")
                .country("US")
                .build()

        when:
        CheckResult result = dynamicChecker.calculate(foundCustomer, inputCustomer)
        System.out.println(result)

        then:
        assert result.addressMatch > 75
    }

    def "DynamicChecker with equals objects"() {
        given:
        NormilizedCustomerData inputCustomer = NormilizedCustomerData.builder()
                .name("samueljackson")
                .address1("address1")
                .region("state")
                .zip("12234")
                .city("City")
                .country("US")
                .build()

        NormilizedCustomerData foundCustomer = NormilizedCustomerData.builder()
                .name("samueljackson")
                .address1("address1")
                .region("state")
                .zip("12234")
                .city("City")
                .country("US")
                .build()

        when:
        CheckResult result = dynamicChecker.calculate(foundCustomer, inputCustomer)

        then:
        assert result.addressMatch > 99
        assert result.nameMatch > 99
        assert result.totalMatch > 99
    }
}
