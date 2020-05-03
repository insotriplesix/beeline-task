package com.github.saboteur.beeline.detailservice.config.properties

import com.github.saboteur.beeline.detailservice.repository.H2Properties
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(
    classes = [DetailServicePropertiesTest.TestConfigProperties::class]
)
@ActiveProfiles("test")
class DetailServicePropertiesTest {

    @Autowired
    lateinit var appProperties: AppProperties

    @Autowired
    lateinit var h2Properties: H2Properties

    @Autowired
    lateinit var postgresProperties: PostgresProperties

    @Autowired
    lateinit var restProperties: RestProperties

    @EnableConfigurationProperties(
        AppProperties::class,
        H2Properties::class,
        PostgresProperties::class,
        RestProperties::class
    )
    class TestConfigProperties {
        // nothing
    }

    @Test
    fun checkProperties() {
        // Check AppProperties
        with (appProperties) {
            Assertions.assertThat(serviceName).isEqualTo("detail-service")
        }

        // Check H2Properties
        with (h2Properties) {
            Assertions.assertThat(driverClassName).isEqualTo("org.h2.Driver")
            Assertions
                .assertThat(url)
                .isEqualTo("jdbc:h2:mem:ds;DB_CLOSE_DELAY=-1;INIT=RUNSCRIPT FROM 'classpath:h2_init.sql'")
            Assertions.assertThat(username).isEqualTo("ds")
            Assertions.assertThat(password).isEqualTo("ds")
        }

        // Check PostgresProperties
        with (postgresProperties) {
            Assertions.assertThat(driverClassName).isEqualTo("org.postgresql.Driver")
            Assertions.assertThat(jdbcUrl).isEqualTo("jdbc:postgresql://localhost:5432/ds")
            Assertions.assertThat(username).isEqualTo("ds")
            Assertions.assertThat(password).isEqualTo("ds")
            Assertions.assertThat(schema).isEqualTo("public")
            Assertions.assertThat(connectionTimeout).isEqualTo(20000L)
            Assertions.assertThat(connectionInitSql).isEqualTo("ALTER ROLE ds SET search_path TO public")
            Assertions.assertThat(connectionTestQuery).isEqualTo("SELECT 1")
        }

        // Check RestProperties
        with (restProperties) {
            Assertions.assertThat(profileServiceUrl).isEqualTo("http://localhost:8941/api/v1/profileservice")
            Assertions.assertThat(establishingConnectionTimeout).isEqualTo(250L)
            Assertions.assertThat(readConnectionTimeout).isEqualTo(1750L)
        }
    }

}