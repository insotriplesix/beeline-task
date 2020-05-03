package com.github.saboteur.beeline.profileservice.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
@ConfigurationProperties(prefix = "app.rest")
data class RestProperties(
    var externalServiceUrl: String = "",
    var externalServiceName: String = "",
    var establishingConnectionTimeout: Int = 250,
    var readConnectionTimeout: Int = 1000,
    var fields: String = ""
)