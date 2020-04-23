package com.github.saboteur.beeline.profileservice.config

import com.github.saboteur.beeline.profileservice.config.properties.PostgresProperties
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(PostgresProperties::class)
class PostgresConfig(
    private val postgresProperties: PostgresProperties
) {

    @Bean(POSTGRES_DATA_SOURCE)
    @FlywayDataSource
    fun getPostgresDataSource(): DataSource =
        HikariDataSource(postgresProperties)

    companion object {
        const val POSTGRES_DATA_SOURCE = "postgresDataSource"
    }

}