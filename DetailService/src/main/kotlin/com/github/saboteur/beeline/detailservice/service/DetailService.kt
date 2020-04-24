package com.github.saboteur.beeline.detailservice.service

import com.github.saboteur.beeline.detailservice.config.properties.RestProperties
import com.github.saboteur.beeline.detailservice.dto.CallerProfile
import com.github.saboteur.beeline.detailservice.dto.external.ProfileDto
import com.github.saboteur.beeline.detailservice.mapper.ProfileToCallerProfileMapper
import com.github.saboteur.beeline.detailservice.repository.SessionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientResponseException
import org.springframework.web.client.RestTemplate
import java.util.concurrent.*

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

        val result = ConcurrentLinkedDeque<CallerProfile>()

        runBlocking {
            ctns.forEach { ctn ->
                launch(Dispatchers.IO) {
                    val url =
                        StringBuilder()
                            .append(restProperties.profileServiceUrl)
                            .append("/getProfileByCtn?ctn=$ctn")
                            .toString()

                    logger.debug { "Thread = ${Thread.currentThread().name}, URL = $url" }

                    try {
                        val response = restTemplate.getForObject(url, ProfileDto::class.java)
                        if (response != null)
                            result.add(ProfileToCallerProfileMapper[response])
                    } catch (e: RestClientResponseException) {
                        logger.error { e.localizedMessage }
                    }
                }
            }
        }

        return result.toList()
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }

}