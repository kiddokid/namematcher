package com.example.filterproject.namematcher.checker

import groovy.util.logging.Slf4j
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT

@ContextConfiguration
@SpringBootTest(webEnvironment = DEFINED_PORT)
@Testcontainers
@Slf4j
abstract class BaseIntegrationTest extends Specification {

    protected static PostgreSQLContainer postgres = new PostgreSQLContainer()
            .withDatabaseName("riskdb")
            .withUsername("kid")
            .withPassword("root")

    def setupSpec() {
        startPostgresIfNeeded()
        ['spring.datasource.url'     : postgres.getJdbcUrl(),
         'spring.datasource.username': postgres.getUsername(),
         'spring.datasource.password': postgres.getPassword()
        ].each { k, v ->
            System.setProperty(k, v)
        }
    }

    private static void startPostgresIfNeeded() {
        if (!postgres.isRunning()) {
            log.info("[BASE-INTEGRATION-TEST] - Postgres is not started. Running...")
            postgres.start()
        }
    }

    def cleanupSpec() {
        if (postgres.isRunning()) {
            log.info("[BASE-INTEGRATION-TEST] - Stopping Postgres...")
            postgres.stop()
        }
    }
}
