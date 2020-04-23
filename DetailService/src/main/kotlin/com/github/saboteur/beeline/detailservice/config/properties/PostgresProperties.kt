package com.github.saboteur.beeline.detailservice.config.properties

import com.zaxxer.hikari.HikariConfig
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
@ConfigurationProperties(prefix = "app.postgres")
class PostgresProperties : HikariConfig()