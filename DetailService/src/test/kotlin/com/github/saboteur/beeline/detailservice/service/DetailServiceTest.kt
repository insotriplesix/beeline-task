package com.github.saboteur.beeline.detailservice.service

import com.github.saboteur.beeline.detailservice.config.properties.RestProperties
import com.github.saboteur.beeline.detailservice.dto.CallerProfile
import com.github.saboteur.beeline.detailservice.dto.external.ProfileDto
import com.github.saboteur.beeline.detailservice.repository.SessionRepository
import com.github.saboteur.beeline.detailservice.service.impl.DetailServiceImpl
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class DetailServiceTest {

    @MockK
    lateinit var sessionRepository: SessionRepository

    @MockK
    lateinit var restProperties: RestProperties

    @MockK
    lateinit var restTemplate: RestTemplate

    @InjectMockKs
    lateinit var detailService: DetailServiceImpl

    @BeforeAll
    fun setUp() {
    }

    @AfterAll
    fun cleanUp() {
        clearAllMocks()
    }

    @Test
    fun getCallerProfileWhenNoSuchCellIdInDatabase() {
        every {
            sessionRepository.findAllCtnByCellId("TEST")
        } returns emptyList()

        val answer = detailService.getCallerProfile("TEST")
        val expected = emptyList<CallerProfile>()

        Assertions.assertThat(answer).isEqualTo(expected)
    }

    @Test
    fun getCallerProfileForValidCellIdButUnavailableProfileService() {
        every {
            sessionRepository.findAllCtnByCellId("TEST")
        } returns listOf("1234567890", "1234567891")

        coEvery {
            restProperties.profileServiceUrl
        } returns "http://localhost:8941/api/v1/profileservice"

        val urls = listOf(
            StringBuilder()
                .append(restProperties.profileServiceUrl)
                .append("/getProfileByCtn?ctn=1234567890")
                .toString(),
            StringBuilder()
                .append(restProperties.profileServiceUrl)
                .append("/getProfileByCtn?ctn=1234567891")
                .toString()
        )

        coEvery {
            restTemplate.getForObject(urls[0], ProfileDto::class.java)
        } throws ResourceAccessException("")

        coEvery {
            restTemplate.getForObject(urls[1], ProfileDto::class.java)
        } throws ResourceAccessException("")

        val answer = detailService.getCallerProfile("TEST")
        val expected = emptyList<CallerProfile>()

        Assertions.assertThat(answer).isEqualTo(expected)
    }

    @Test
    fun getCallerProfileForValidCellIdAndAvailableProfileService() {
        every {
            sessionRepository.findAllCtnByCellId("TEST")
        } returns listOf("1234567890", "1234567891")

        coEvery {
            restProperties.profileServiceUrl
        } returns "http://localhost:8941/api/v1/profileservice"

        val urls = listOf(
            StringBuilder()
                .append(restProperties.profileServiceUrl)
                .append("/getProfileByCtn?ctn=1234567890")
                .toString(),
            StringBuilder()
                .append(restProperties.profileServiceUrl)
                .append("/getProfileByCtn?ctn=1234567891")
                .toString()
        )

        coEvery {
            restTemplate.getForObject(urls[0], ProfileDto::class.java)
        } returns ProfileDto(
            ctn = "1234567890",
            callerId = "TEST",
            name = "",
            email = ""
        )

        coEvery {
            restTemplate.getForObject(urls[1], ProfileDto::class.java)
        } returns ProfileDto(
            ctn = "1234567891",
            callerId = "TEST",
            name = "",
            email = ""
        )

        val answer = detailService.getCallerProfile("TEST")
        val expected = listOf(
            CallerProfile(
                ctn = "1234567890",
                callerId = "TEST",
                name = "",
                email = ""
            ),
            CallerProfile(
                ctn = "1234567891",
                callerId = "TEST",
                name = "",
                email = ""
            )
        )

        Assertions.assertThat(answer).containsAll(expected)
    }

}