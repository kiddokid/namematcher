package com.example.filterproject.namematcher.checker.repository

import com.example.filterproject.namematcher.checker.BaseIntegrationTest
import com.example.filterproject.namematcher.dao.NormilizedCustomerDataRepository
import com.example.filterproject.namematcher.model.NormilizedCustomerData
import org.springframework.beans.factory.annotation.Autowired

class NormilizedCustomerDataRepositoryIntSpec extends BaseIntegrationTest {

    @Autowired
    private NormilizedCustomerDataRepository repository

    def setup() {
        repository?.deleteAllInBatch()
    }

    def "findAll returns correct result"() {
        NormilizedCustomerData data = NormilizedCustomerData.builder()
        .riskCustomerId(1)
        .name("abc dfc")
        .address1("address str1")
        .address2("address2 str2")
        .region("region")
        .city("city")
        .zip("100234")
        .country("PL")
        .build()

        when:
        repository.save(data)

        then:
        List<NormilizedCustomerData> dataList = repository.findAll()
        NormilizedCustomerData first = dataList.get(0)
        assert first.getRiskCustomerId() == 1
    }

    def "getByRiskCustomerId returns correct result"() {
        NormilizedCustomerData data = NormilizedCustomerData.builder()
                .riskCustomerId(123)
                .name("abc dfc")
                .address1("address str1")
                .address2("address2 str2")
                .region("region")
                .city("city")
                .zip("100234")
                .country("PL")
                .build()

        when:
        repository.save(data)

        then:
        NormilizedCustomerData result = repository.findByRiskCustomerId(123)
        assert result.getRiskCustomerId() == 123
    }
}
