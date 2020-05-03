package com.github.saboteur.beeline.profileservice.controller

import com.github.saboteur.beeline.profileservice.dto.ProfileDto
import com.github.saboteur.beeline.profileservice.service.impl.ProfileServiceImpl
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
@WebFluxTest(ProfileServiceControllerApi::class)
class ProfileControllerTest {

    @TestConfiguration
    class TestConfig {
        @Bean
        fun detailService() = mockk<ProfileServiceImpl>()
    }

    @Autowired
    lateinit var webClient: WebTestClient

    @Autowired
    lateinit var profileService: ProfileServiceImpl

    @Test
    fun testApiVersion() {
        Assertions.assertThat(ProfileServiceControllerApi.Companion).isNotNull
        Assertions.assertThat(ProfileServiceControllerApi.API_VERSION).isEqualTo("v1")
    }

    @Test
    fun getProfileByCtnWhenDataUnavailable() {
        every {
            profileService.getProfile(any())
        } returns ProfileDto.empty

        webClient
            .get()
            .uri("/api/v1/profileservice/getProfileByCtn?ctn={ctn}", "TEST")
            .header(HttpHeaders.ACCEPT, "application/json")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.ctn").isEqualTo("")
            .jsonPath("$.callerId").isEqualTo("")
            .jsonPath("$.name").isEqualTo("")
            .jsonPath("$.email").isEqualTo("")

        verify(atLeast = 1) {
            profileService.getProfile(any())
        }
    }

    @ParameterizedTest
    @ArgumentsSource(ProfileControllerDataProvider.GetProfile::class)
    fun getProfileByCtnWhenDataProvided(providedProfile: ProfileDto) {
        every {
            profileService.getProfile(any())
        } returns providedProfile

        webClient
            .get()
            .uri("/api/v1/profileservice/getProfileByCtn?ctn={ctn}", "TEST")
            .header(HttpHeaders.ACCEPT, "application/json")
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.ctn").isEqualTo(providedProfile.ctn)
            .jsonPath("$.callerId").isEqualTo(providedProfile.callerId)
            .jsonPath("$.name").isEqualTo(providedProfile.name)
            .jsonPath("$.email").isEqualTo(providedProfile.email)

        verify(atLeast = 1) {
            profileService.getProfile(any())
        }
    }

}