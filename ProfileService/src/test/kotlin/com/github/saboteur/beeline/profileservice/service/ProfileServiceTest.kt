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
import org.junit.jupiter.params.provider.ArgumentsSources
import org.springframework.web.client.RestClientResponseException
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
        // setup stub
    }

    @AfterAll
    fun cleanUp() {
        clearAllMocks()
    }

    @ParameterizedTest
    @ArgumentsSource(ProfileServiceDataProvider.GetProfileWithoutCallerId::class)
    fun getProfileWhenNoSuchCtnInDatabase(
        providedProfile: ProfileDto,
        providedRandomUserProfileDto: RandomUserProfileDto,
        providedRestProperties: RestProperties,
        providedReqUrl: String
    ) {
        every {
            callerRepository.findCallerIdByCtn("TEST")
        } returns ""

        every {
            restProperties.externalServiceName
        } returns providedRestProperties.externalServiceName

        every {
            restProperties.externalServiceUrl
        } returns providedRestProperties.externalServiceUrl

        every {
            restProperties.fields
        } returns providedRestProperties.fields

        every {
            restTemplate.getForObject(providedReqUrl, RandomUserProfileDto::class.java)
        } returns providedRandomUserProfileDto

        val answer = profileService.getProfile("TEST")

        Assertions.assertThat(answer).isEqualTo(providedProfile)
    }

    @ParameterizedTest
    @ArgumentsSources(
        ArgumentsSource(ProfileServiceDataProvider.GetProfileWithCallerId::class),
        ArgumentsSource(ProfileServiceDataProvider.GetProfileWithError::class)
    )
    fun getProfileWhenSuchCtnInDatabaseOrAnErrorInExternalService(
        providedProfile: ProfileDto,
        providedRandomUserProfileDto: RandomUserProfileDto,
        providedCallerId: String,
        providedRestProperties: RestProperties,
        providedReqUrl: String
    ) {
        every {
            callerRepository.findCallerIdByCtn("1234567890")
        } returns providedCallerId

        every {
            restProperties.externalServiceName
        } returns providedRestProperties.externalServiceName

        every {
            restProperties.externalServiceUrl
        } returns providedRestProperties.externalServiceUrl

        every {
            restProperties.fields
        } returns providedRestProperties.fields

        every {
            restTemplate.getForObject(providedReqUrl, RandomUserProfileDto::class.java)
        } returns providedRandomUserProfileDto

        val answer = profileService.getProfile("1234567890")

        Assertions.assertThat(answer).isEqualTo(providedProfile)
    }

    @ParameterizedTest
    @ArgumentsSource(ProfileServiceDataProvider.GetProfileWithUnknownType::class)
    fun getProfileWhenUnknownTypeOfExternalServiceProvided(
        providedProfile: ProfileDto
    ) {
        every {
            callerRepository.findCallerIdByCtn("TEST")
        } returns ""

        every {
            restProperties.externalServiceName
        } returns "unknown"

        val answer = profileService.getProfile("TEST")

        Assertions.assertThat(answer).isEqualTo(providedProfile)
    }


    @ParameterizedTest
    @ArgumentsSource(ProfileServiceDataProvider.GetProfileWhenExceptionThrown::class)
    fun getProfileWhenExceptionThrown(
        providedProfile: ProfileDto,
        providedRestProperties: RestProperties,
        providedReqUrl: String
    ) {
        every {
            callerRepository.findCallerIdByCtn("TEST")
        } returns ""

        every {
            restProperties.externalServiceName
        } returns providedRestProperties.externalServiceName

        every {
            restProperties.externalServiceUrl
        } returns providedRestProperties.externalServiceUrl

        every {
            restProperties.fields
        } returns providedRestProperties.fields

        every {
            restTemplate.getForObject(providedReqUrl, RandomUserProfileDto::class.java)
        } throws RestClientResponseException("message", 0, "status", null, null, null)

        val answer = profileService.getProfile("TEST")

        Assertions.assertThat(answer).isEqualTo(providedProfile)
    }

}