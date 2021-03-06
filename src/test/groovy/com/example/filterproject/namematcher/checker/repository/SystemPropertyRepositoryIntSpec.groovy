package com.example.filterproject.namematcher.checker.repository

import com.example.filterproject.namematcher.checker.BaseIntegrationTest
import com.example.filterproject.namematcher.dao.SystemPropertyRepository
import com.example.filterproject.namematcher.model.SystemProperty
import org.springframework.beans.factory.annotation.Autowired

class SystemPropertyRepositoryIntSpec extends BaseIntegrationTest {

    @Autowired
    private SystemPropertyRepository systemPropertyRepository

    def setup() {
        systemPropertyRepository?.deleteAllInBatch()
    }

    def "findAll return all entities correctly"() {
        given:
        SystemProperty systemProperty = SystemProperty.builder()
                .property("NORMALIZED_TEXT_SAVE_OFFSET")
                .value("3240fjsodif43rbfdsbfkjsdf")
                .build()

        systemPropertyRepository.save(systemProperty)

        when:
        List<SystemProperty> systemProperties = systemPropertyRepository.findAll()

        then:
        assert systemProperties.size() == 1
        assert systemProperties.get(0).property == "NORMALIZED_TEXT_SAVE_OFFSET"
        assert systemProperties.get(0).value == "3240fjsodif43rbfdsbfkjsdf"

        when:
        setup()
        systemPropertyRepository.save(systemProperty)

        then:
        Optional<SystemProperty> systemProperty1 = systemPropertyRepository.findById("NORMALIZED_TEXT_SAVE_OFFSET")
        assert systemProperty1.get().getValue() == "3240fjsodif43rbfdsbfkjsdf"
    }
}
