package com.github.saboteur.beeline.profileservice.config

import com.github.saboteur.beeline.profileservice.config.properties.RestProperties
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension::class)
@SpringBootTest(
    classes = [
        RestProperties::class,
        RestConfig::class,
        SwaggerConfig::class
    ]
)
@ActiveProfiles("test")
class ProfileServiceConfigTest {

    @MockK
    lateinit var postgresConfig: PostgresConfig

    @Autowired
    lateinit var swaggerConfig: SwaggerConfig

    @Autowired
    lateinit var restConfig: RestConfig

    @BeforeAll
    fun setUp() {
        postgresConfig = mockk()
    }

    @AfterAll
    fun cleanUp() {
        clearAllMocks()
    }

    @Test
    fun checkConfigs() {
        // Check PostgresConfig
        Assertions.assertThat(PostgresConfig.POSTGRES_DATA_SOURCE).isEqualTo("postgresDataSource")

        every {
            postgresConfig.getPostgresDataSource()
        } returns mockk()

        Assertions.assertThat(postgresConfig.getPostgresDataSource()).isNotNull

        // Check SwaggerConfig
        Assertions.assertThat(SwaggerConfig.API_VERSION).isEqualTo("v1")
        Assertions.assertThat(swaggerConfig.api()).isNotNull

        // Check RestConfig
        with (restConfig) {
            Assertions.assertThat(restTemplate()).isNotNull
            Assertions.assertThat(restTemplate().requestFactory).isNotNull
        }
    }

}