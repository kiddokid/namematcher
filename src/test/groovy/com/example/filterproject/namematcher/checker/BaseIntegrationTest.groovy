package com.example.filterproject.namematcher.checker

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT

@Testcontainers
@ContextConfiguration
@SpringBootTest(webEnvironment = DEFINED_PORT)
abstract class BaseIntegrationTest extends Specification {

    protected static PostgreSQLContainer postgres = new PostgreSQLContainer()
            .withDatabaseName("riskdb")
            .withUsername("kid")
            .withPassword("root")

    def setupSpec() {
        startPosgresIfNeeded()
        ['spring.datasource.url'     : postgres.getJdbcUrl(),
         'spring.datasource.username': postgres.getUsername(),
         'spring.datasource.password': postgres.getPassword()
        ].each { k, v ->
            System.setProperty(k, v)
        }
    }

    private static void startPosgresIfNeeded() {
        if (!postgres.isRunning()) {
            postgres.start()
        }
    }

    def cleanupSpec() {
        if (postgres.isRunning()) {
            postgres.stop()
        }
    }
}
