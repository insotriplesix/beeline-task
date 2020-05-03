package com.github.saboteur.beeline.detailservice.controller

import com.github.saboteur.beeline.detailservice.dto.CallerProfile
import com.github.saboteur.beeline.detailservice.service.DetailService
import com.github.saboteur.beeline.detailservice.service.impl.DetailServiceImpl
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpHeaders
import org.springframework.test.web.reactive.server.WebTestClient

@ExtendWith(MockKExtension::class)
@WebFluxTest(DetailServiceControllerApi::class)
class DetailControllerTest {

    @TestConfiguration
    class TestConfig {
        @Bean
        fun detailService() = mockk<DetailServiceImpl>()
    }

    @Autowired
    lateinit var webClient: WebTestClient

    @Autowired
    lateinit var detailService: DetailService

    @Test
    fun testApiVersion() {
        Assertions.assertThat(DetailServiceControllerApi.Companion).isNotNull
        Assertions.assertThat(DetailServiceControllerApi.API_VERSION).isEqualTo("v1")
    }

    @Test
    fun getCallerProfileByCellIdWhenNoDataFound() {
        every {
            detailService.getCallerProfile(any())
        } returns emptyList()

        webClient
            .get()
            .uri("/api/v1/detailservice/getCallerProfileByCellId?cellId={cellId}", "TEST")
            .header(HttpHeaders.ACCEPT, "application/json")
            .exchange()
            .expectStatus().isNotFound
            .expectBody()
            .json("[]")

        verify(atLeast = 1) {
            detailService.getCallerProfile(any())
        }
    }

    @ParameterizedTest
    @ArgumentsSource(DetailControllerDataProvider.GetCallerProfile::class)
    fun getCallerProfileByCellIdWhenDataProvided(providedCallerProfile: CallerProfile) {
        every {
            detailService.getCallerProfile(any())
        } returns listOf(providedCallerProfile)

        webClient
            .get()
            .uri("/api/v1/detailservice/getCallerProfileByCellId?cellId={cellId}", "TEST")
            .header(HttpHeaders.ACCEPT, "application/json")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$[0].ctn").isEqualTo(providedCallerProfile.ctn)
            .jsonPath("$[0].callerId").isEqualTo(providedCallerProfile.callerId)
            .jsonPath("$[0].name").isEqualTo(providedCallerProfile.name)
            .jsonPath("$[0].email").isEqualTo(providedCallerProfile.email)

        verify(atLeast = 1) {
            detailService.getCallerProfile(any())
        }
    }

}