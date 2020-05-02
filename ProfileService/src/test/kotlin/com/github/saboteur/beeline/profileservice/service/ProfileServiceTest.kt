package com.github.saboteur.beeline.profileservice.service

import com.github.saboteur.beeline.profileservice.config.properties.RestProperties
import com.github.saboteur.beeline.profileservice.dto.ProfileDto
import com.github.saboteur.beeline.profileservice.dto.external.randomuser.RandomUserProfileDto
import com.github.saboteur.beeline.profileservice.repository.CallerRepository
import com.github.saboteur.beeline.profileservice.service.impl.ProfileServiceImpl
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import org.springframework.web.client.RestTemplate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class ProfileServiceTest {

    @MockK
    lateinit var callerRepository: CallerRepository

    @MockK
    lateinit var restProperties: RestProperties

    @MockK
    lateinit var restTemplate: RestTemplate

    @InjectMockKs
    lateinit var profileService: ProfileServiceImpl

    @BeforeAll
    fun setUp() {
    }

    @AfterAll
    fun cleanUp() {
        clearAllMocks()
    }

    @ParameterizedTest
    @ArgumentsSource(ProfileServiceDataProvider.GetProfileWithoutCallerId::class)
    fun getProfileWhenNoSuchCtnInDatabase(
        providedProfile: ProfileDto,
        providedRandomUserProfileDto: RandomUserProfileDto
    ) {
        every {
            callerRepository.findCallerIdByCtn("TEST")
        } returns ""

        every {
            restProperties.externalServiceName
        } returns "randomuser"

        every {
            restProperties.externalServiceUrl
        } returns "externalServiceUrl"

        every {
            restProperties.fields
        } returns "includedFields"

        val url =
            StringBuilder()
                .append(restProperties.externalServiceUrl)
                .append("/api/?phone=")
                .append("TEST")
                .append("&inc=")
                .append(restProperties.fields)
                .toString()

        every {
            restTemplate.getForObject(url, RandomUserProfileDto::class.java)
        } returns providedRandomUserProfileDto

        val answer = profileService.getProfile("TEST")

        Assertions.assertThat(answer).isEqualTo(providedProfile)
    }

    @ParameterizedTest
    @ArgumentsSource(ProfileServiceDataProvider.GetProfileWithCallerId::class)
    fun getProfileWhenSuchCtnInDatabase(
        providedProfile: ProfileDto,
        providedRandomUserProfileDto: RandomUserProfileDto,
        providedCallerId: String
    ) {
        every {
            callerRepository.findCallerIdByCtn("1234567890")
        } returns providedCallerId

        every {
            restProperties.externalServiceName
        } returns "randomuser"

        every {
            restProperties.externalServiceUrl
        } returns "externalServiceUrl"

        every {
            restProperties.fields
        } returns "includedFields"

        val url =
            StringBuilder()
                .append(restProperties.externalServiceUrl)
                .append("/api/?phone=")
                .append("1234567890")
                .append("&inc=")
                .append(restProperties.fields)
                .toString()

        every {
            restTemplate.getForObject(url, RandomUserProfileDto::class.java)
        } returns providedRandomUserProfileDto

        val answer = profileService.getProfile("1234567890")

        Assertions.assertThat(answer).isEqualTo(providedProfile)
    }

    @ParameterizedTest
    @ArgumentsSource(ProfileServiceDataProvider.GetProfileWithError::class)
    fun getProfileWhenErrorInExternalService(
        providedProfile: ProfileDto,
        providedRandomUserProfileDto: RandomUserProfileDto,
        providedCallerId: String
    ) {
        every {
            callerRepository.findCallerIdByCtn("1234567890")
        } returns providedCallerId

        every {
            restProperties.externalServiceName
        } returns "randomuser"

        every {
            restProperties.externalServiceUrl
        } returns "externalServiceUrl"

        every {
            restProperties.fields
        } returns "includedFields"

        val url =
            StringBuilder()
                .append(restProperties.externalServiceUrl)
                .append("/api/?phone=")
                .append("1234567890")
                .append("&inc=")
                .append(restProperties.fields)
                .toString()

        every {
            restTemplate.getForObject(url, RandomUserProfileDto::class.java)
        } returns providedRandomUserProfileDto

        val answer = profileService.getProfile("1234567890")

        Assertions.assertThat(answer).isEqualTo(providedProfile)
    }

}