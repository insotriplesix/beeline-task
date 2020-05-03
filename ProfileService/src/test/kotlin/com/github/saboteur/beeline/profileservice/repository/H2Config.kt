package com.github.saboteur.beeline.profileservice.repository

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
@EnableConfigurationProperties(H2Properties::class)
class H2Config(
    private val h2Properties: H2Properties
) {

    @Bean
    @Profile("test")
    fun getH2DataSource(): DataSource {
        val dataSource = DriverManagerDataSource()

        with (dataSource) {
            setDriverClassName(h2Properties.driverClassName)
            url = h2Properties.url
            username = h2Properties.username
            password = h2Properties.password
        }

        return dataSource
    }

    @Bean
    fun jdbcTemplate(dataSource: DataSource) =
        JdbcTemplate(dataSource)

}