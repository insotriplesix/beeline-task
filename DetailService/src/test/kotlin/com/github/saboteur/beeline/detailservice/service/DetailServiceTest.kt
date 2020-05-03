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
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestClientResponseException
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
        // setup stub
    }

    @AfterAll
    fun cleanUp() {
        clearAllMocks()
    }

    @Test
    fun getCallerProfileWhenNoSuchCellIdInDatabase() {
        every {
            sessionRepository.findAllCtnsByCellId(testCellId)
        } returns emptyList()

        val answer = detailService.getCallerProfile(testCellId)
        val expected = emptyList<CallerProfile>()

        Assertions.assertThat(answer).isEqualTo(expected)
    }

    @Test
    fun getCallerProfileForValidCellIdButUnavailableProfileService() {
        every {
            sessionRepository.findAllCtnsByCellId(testCellId)
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

        val answer = detailService.getCallerProfile(testCellId)
        val expected = emptyList<CallerProfile>()

        Assertions.assertThat(answer).isEqualTo(expected)
    }

    @Test
    fun getCallerProfileForValidCellIdButExceptionThrown() {
        every {
            sessionRepository.findAllCtnsByCellId(testCellId)
        } returns listOf("555555555", "444444444")

        coEvery {
            restProperties.profileServiceUrl
        } returns "http://localhost:8941/api/v1/profileservice"

        val urls = listOf(
            StringBuilder()
                .append(restProperties.profileServiceUrl)
                .append("/getProfileByCtn?ctn=555555555")
                .toString(),
            StringBuilder()
                .append(restProperties.profileServiceUrl)
                .append("/getProfileByCtn?ctn=444444444")
                .toString()
        )

        coEvery {
            restTemplate.getForObject(urls[0], ProfileDto::class.java)
        } throws RestClientResponseException("message", 0, "status", null, null, null)

        coEvery {
            restTemplate.getForObject(urls[1], ProfileDto::class.java)
        } throws RestClientResponseException("message", 0, "status", null, null, null)

        val answer = detailService.getCallerProfile(testCellId)
        val expected = emptyList<CallerProfile>()

        Assertions.assertThat(answer).isEqualTo(expected)
    }

    @ParameterizedTest
    @ArgumentsSource(DetailServiceDataProvider.GetCallerProfile::class)
    fun getCallerProfileForValidCellIdAndAvailableProfileService(
        providedCallerProfiles: List<CallerProfile>,
        providedProfileDtos: List<ProfileDto>,
        providedCtns: List<String>
    ) {
        every {
            sessionRepository.findAllCtnsByCellId(testCellId)
        } returns providedCtns

        coEvery {
            restProperties.profileServiceUrl
        } returns "http://localhost:8941/api/v1/profileservice"

        val urls = listOf(
            StringBuilder()
                .append(restProperties.profileServiceUrl)
                .append("/getProfileByCtn?ctn=${providedCtns[0]}")
                .toString(),
            StringBuilder()
                .append(restProperties.profileServiceUrl)
                .append("/getProfileByCtn?ctn=${providedCtns[1]}")
                .toString()
        )

        coEvery {
            restTemplate.getForObject(urls[0], ProfileDto::class.java)
        } returns providedProfileDtos[0]

        coEvery {
            restTemplate.getForObject(urls[1], ProfileDto::class.java)
        } returns providedProfileDtos[1]

        val answer = detailService.getCallerProfile(testCellId)

        Assertions.assertThat(answer).containsAll(providedCallerProfiles)
    }

    companion object {
        const val testCellId = "TEST"
    }

}