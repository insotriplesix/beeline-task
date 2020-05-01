package com.github.saboteur.beeline.profileservice.service.impl

import com.github.saboteur.beeline.profileservice.config.properties.RestProperties
import com.github.saboteur.beeline.profileservice.dto.ProfileDto
import com.github.saboteur.beeline.profileservice.dto.external.randomuser.RandomUserProfileDto
import com.github.saboteur.beeline.profileservice.mapper.AggregatedInfoToProfileMapper
import com.github.saboteur.beeline.profileservice.model.AggregatedInfo
import com.github.saboteur.beeline.profileservice.model.ExternalServiceType
import com.github.saboteur.beeline.profileservice.repository.CallerRepository
import com.github.saboteur.beeline.profileservice.service.ProfileService
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientResponseException
import org.springframework.web.client.RestTemplate

@Service
class ProfileServiceImpl(
    private val sessionRepository: CallerRepository,
    private val restProperties: RestProperties,
    private val restTemplate: RestTemplate
) : ProfileService {

    override fun getProfile(ctn: String): ProfileDto {
        val aggregatedInfo = getAggregatedInfo(ctn)
        return AggregatedInfoToProfileMapper[aggregatedInfo]
    }

    private fun getAggregatedInfo(ctn: String): AggregatedInfo {
        val profile = getProfileFromExternalService(ctn)
        val callerId = sessionRepository.findCallerIdByCtn(ctn)

        return AggregatedInfo(
            ctn = ctn,
            callerId = callerId,
            gender = profile?.results?.get(0)?.gender,
            titleName = profile?.results?.get(0)?.name?.title,
            firstName = profile?.results?.get(0)?.name?.first,
            lastName = profile?.results?.get(0)?.name?.last,
            email = profile?.results?.get(0)?.email
        ).also {
            logger.debug { "Aggregated info: $it" }
        }
    }

    private fun getProfileFromExternalService(ctn: String): RandomUserProfileDto? =
        when (restProperties.externalServiceName) {
            ExternalServiceType.RANDOM_USER.serviceName -> getProfileFromRandomUserService(ctn)
            else -> {
                logger.info {
                    "Can't find an appropriate type for external service: ${restProperties.externalServiceName}"
                }
                null
            }
        }

    private fun getProfileFromRandomUserService(ctn: String): RandomUserProfileDto? {
        val url =
            StringBuilder()
                .append(restProperties.externalServiceUrl)
                .append("/api/?phone=")
                .append(ctn)
                .append("&inc=")
                .append(restProperties.fields)
                .toString()

        logger.debug { "Thread = ${Thread.currentThread().name}, URL = $url" }

        var result: RandomUserProfileDto? = null

        try {
            result = restTemplate.getForObject(url, RandomUserProfileDto::class.java)
            if (result?.error != null) {
                logger.info { "Cannot fetch data from external service: $result.error" }
            }
        } catch (e: RestClientResponseException) {
            logger.error { "An exception occurred for CTN = $ctn: " + e.localizedMessage }
        }

        return result
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }

}