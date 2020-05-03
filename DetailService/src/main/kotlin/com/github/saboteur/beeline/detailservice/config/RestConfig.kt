package com.github.saboteur.beeline.detailservice.config

import com.github.saboteur.beeline.detailservice.config.properties.RestProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class RestConfig(
    private val restProperties: RestProperties
) {

    @Bean
    fun restTemplate(): RestTemplate {
        val clientRequestFactory =
            HttpComponentsClientHttpRequestFactory()

        clientRequestFactory.setConnectTimeout(restProperties.establishingConnectionTimeout)
        clientRequestFactory.setReadTimeout(restProperties.readConnectionTimeout)

        return RestTemplate(clientRequestFactory)
    }

}