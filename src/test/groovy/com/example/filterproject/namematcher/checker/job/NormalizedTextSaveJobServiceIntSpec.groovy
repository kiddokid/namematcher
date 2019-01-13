package com.example.filterproject.namematcher.checker.job

import com.example.filterproject.namematcher.checker.BaseIntegrationTest
import com.example.filterproject.namematcher.dao.NormilizedCustomerDataRepository
import com.example.filterproject.namematcher.dao.RiskCustomerRepository
import com.example.filterproject.namematcher.dao.SystemPropertyRepository
import com.example.filterproject.namematcher.job.service.NormalizedTextSaveJobService
import com.example.filterproject.namematcher.model.NormilizedCustomerData
import com.example.filterproject.namematcher.model.RiskCustomer
import com.example.filterproject.namematcher.model.SystemProperty
import org.springframework.beans.factory.annotation.Autowired

class NormalizedTextSaveJobServiceIntSpec extends BaseIntegrationTest {

    @Autowired
    private NormalizedTextSaveJobService normalizedTextSaveJobService
    @Autowired
    private NormilizedCustomerDataRepository normilizedCustomerDataRepository
    @Autowired
    private SystemPropertyRepository systemPropertyRepository
    @Autowired
    private RiskCustomerRepository riskCustomerRepository

    def setup() {
        riskCustomerRepository?.deleteAllInBatch()
        systemPropertyRepository?.deleteAllInBatch()
        normilizedCustomerDataRepository?.deleteAllInBatch()
    }

    def cleanup() {
        systemPropertyRepository?.deleteAllInBatch()
        riskCustomerRepository?.deleteAllInBatch()
        normilizedCustomerDataRepository?.deleteAllInBatch()
    }

    def "Migration of data works correct with empty offset"() {
        riskCustomerRepository.saveAll([RiskCustomer.builder()
                                                .firstName("Samuel")
                                                .middleName("L")
                                                .lastName("Jackson")
                                                .email("email2@email.com")
                                                .address1("6927 Jana Junction")
                                                .region_state("Florida")
                                                .zip("12234")
                                                .city("Kape Town")
                                                .country("US")
                                                .reason("some reason")
                                                .build()])
        when:
        normalizedTextSaveJobService.save()

        then:
        List<NormilizedCustomerData> dataList = normilizedCustomerDataRepository.findAll()
        assert dataList.size() == 1
        NormilizedCustomerData normilizedCustomerData = dataList.get(0)
        assert normilizedCustomerData.getName() == "jacksonsamuel"
        assert normilizedCustomerData.getRiskCustomerId() == 1
        assert normilizedCustomerData.getAddress1() == "6927janajunction"
        assert normilizedCustomerData.getAddress2() == ""
        assert normilizedCustomerData.getRegion() == "florida"
        assert normilizedCustomerData.getCity() == "kapetown"
        assert normilizedCustomerData.getZip() == 12234.toString()
        assert normilizedCustomerData.getCountry() == "us"
    }

    def "Migration continues from offset"() {
        riskCustomerRepository.saveAll([RiskCustomer.builder()
                                                .firstName("Samuel")
                                                .middleName("L")
                                                .lastName("Jackson")
                                                .email("email2@email.com")
                                                .address1("6927 Jana Junction")
                                                .region_state("Florida")
                                                .zip("12234")
                                                .city("Kape Town")
                                                .country("US")
                                                .reason("some reason")
                                                .build()
                                        , RiskCustomer.builder()
                                                .firstName("Michel")
                                                .middleName("L")
                                                .lastName("Owen")
                                                .email("michelowen@email.com")
                                                .address1("123 Rochestdail")
                                                .region_state("Florida")
                                                .zip("12234")
                                                .city("London")
                                                .country("UK")
                                                .reason("some reason")
                                                .build()])
        systemPropertyRepository.save(SystemProperty.builder()
                .property("NORMALIZED_TEXT_SAVE_OFFSET")
                .value("2")
                .build())

        when:
        normalizedTextSaveJobService.save()

        then:
        List<NormilizedCustomerData> dataList = normilizedCustomerDataRepository.findAll()
        assert dataList.size() == 1
    }

    def "Nothing to migrate not throwing any exceptions"() {
        riskCustomerRepository.saveAll([RiskCustomer.builder()
                                                .firstName("Samuel")
                                                .middleName("L")
                                                .lastName("Jackson")
                                                .email("email2@email.com")
                                                .address1("6927 Jana Junction")
                                                .region_state("Florida")
                                                .zip("12234")
                                                .city("Kape Town")
                                                .country("US")
                                                .reason("some reason")
                                                .build()
                                        , RiskCustomer.builder()
                                                .firstName("Michel")
                                                .middleName("L")
                                                .lastName("Owen")
                                                .email("michelowen@email.com")
                                                .address1("123 Rochestdail")
                                                .region_state("Florida")
                                                .zip("12234")
                                                .city("London")
                                                .country("UK")
                                                .reason("some reason")
                                                .build()])
        systemPropertyRepository.save(SystemProperty.builder()
                .property("NORMALIZED_TEXT_SAVE_OFFSET")
                .value("5")
                .build())

        when:
        normalizedTextSaveJobService.save()

        then:
        List<NormilizedCustomerData> dataList = normilizedCustomerDataRepository.findAll()
        assert dataList.size() == 0
    }
}
