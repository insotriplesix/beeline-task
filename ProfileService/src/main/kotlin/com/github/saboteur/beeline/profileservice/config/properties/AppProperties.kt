package com.github.saboteur.beeline.profileservice.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
@ConfigurationProperties(prefix = "app")
data class AppProperties(
    var serviceName: String = ""
)