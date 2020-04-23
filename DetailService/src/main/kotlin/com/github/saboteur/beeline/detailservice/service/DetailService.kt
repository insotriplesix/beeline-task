package com.github.saboteur.beeline.detailservice.service

import com.github.saboteur.beeline.detailservice.config.properties.RestProperties
import com.github.saboteur.beeline.detailservice.dto.CallerProfile
import com.github.saboteur.beeline.detailservice.dto.external.ProfileDto
import com.github.saboteur.beeline.detailservice.mapper.ProfileToCallerProfileMapper
import com.github.saboteur.beeline.detailservice.repository.SessionRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class DetailService(
    private val sessionRepository: SessionRepository,
    private val restProperties: RestProperties,
    private val restTemplate: RestTemplate
) {

    fun getCallerProfile(cellId: String): List<CallerProfile> {
        val ctns = sessionRepository.findAllCtnByCellId(cellId)

        if (ctns.isEmpty()) {
            logger.info { "CTNs for Cell ID = $cellId weren't found in database" }
            return emptyList()
        }

        val result = mutableListOf<CallerProfile>()

        // TODO: add asynchronous processing
        for (ctn in ctns) {
            val url =
                StringBuilder()
                    .append(restProperties.profileServiceUrl)
                    .append("/getProfileByCtn?ctn=$ctn")
                    .toString()

            try {
                val response = restTemplate.getForObject(url, ProfileDto::class.java)
                if (response != null)
                    result.add(ProfileToCallerProfileMapper[response])
            } catch (e: HttpClientErrorException) {
                logger.error { e.localizedMessage }
            }
        }

        return result
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }

}