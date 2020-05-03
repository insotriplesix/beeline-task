package com.github.saboteur.beeline.profileservice.repository

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
@ConfigurationProperties(prefix = "app.h2")
data class H2Properties(
    var driverClassName: String = "",
    var url: String = "",
    var username: String = "",
    var password: String = ""
)