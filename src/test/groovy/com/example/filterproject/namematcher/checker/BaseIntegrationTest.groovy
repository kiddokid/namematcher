package com.example.filterproject.namematcher.checker

import groovy.util.logging.Slf4j
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT

@ContextConfiguration
@SpringBootTest(webEnvironment = DEFINED_PORT)
@Testcontainers
@ActiveProfiles('test')
@Slf4j
abstract class BaseIntegrationTest extends Specification {

    protected static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:9.6.2")
            .withDatabaseName("test")
            .withUsername("test")
            .withPassword("test")


    def setupSpec() {
        startPostgresIfNeeded()
        ['spring.datasource.url'     : postgres.getJdbcUrl(),
         'spring.datasource.username': postgres.getUsername(),
         'spring.datasource.password': postgres.getPassword(),
         'spring.datasource.hikari.jdbc-url': postgres.getJdbcUrl(),
//         'spring.jpa.database-platform':'org.hibernate.dialect.PostgreSQL95Dialect',
//         'spring.jpa.properties.hibertnate.default_schema': 'namematching',
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

//    def cleanupSpec() {
//        if (postgres.isRunning()) {
//            log.info("[BASE-INTEGRATION-TEST] - Stopping Postgres...")
//            postgres.stop()
//        }
//    }
}
