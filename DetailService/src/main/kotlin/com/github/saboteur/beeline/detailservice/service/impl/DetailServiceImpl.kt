package com.github.saboteur.beeline.detailservice.service.impl

import com.github.saboteur.beeline.detailservice.config.properties.RestProperties
import com.github.saboteur.beeline.detailservice.dto.CallerProfile
import com.github.saboteur.beeline.detailservice.dto.external.ProfileDto
import com.github.saboteur.beeline.detailservice.mapper.ProfileToCallerProfileMapper
import com.github.saboteur.beeline.detailservice.repository.SessionRepository
import com.github.saboteur.beeline.detailservice.service.DetailService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestClientResponseException
import org.springframework.web.client.RestTemplate
import java.util.concurrent.*
import kotlin.system.measureTimeMillis

@Service
class DetailServiceImpl(
    private val sessionRepository: SessionRepository,
    private val restProperties: RestProperties,
    private val restTemplate: RestTemplate
) : DetailService {

    override fun getCallerProfile(cellId: String): List<CallerProfile> {
        val ctns =
            sessionRepository
                .findAllCtnsByCellId(cellId)
                .also {
                    if (it.isEmpty()) {
                        logger.info { "CTNs for Cell ID = $cellId weren't found in database" }
                        return emptyList()
                    }
                }

        val result = ConcurrentLinkedQueue<CallerProfile>()

        measureTimeMillis {
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
                            restTemplate
                                .getForObject(url, ProfileDto::class.java)
                                ?.let {
                                    result.add(ProfileToCallerProfileMapper[it])
                                }
                        } catch (e: ResourceAccessException) {
                            logger.error { "Resource unavailable for CTN = $ctn: " + e.localizedMessage }
                        } catch (e: RestClientResponseException) {
                            logger.error { "An exception occurred for CTN = $ctn: " + e.localizedMessage }
                        }
                    }
                }
            }
        }.also { time ->
            logger.info {
                "Request for Cell ID = $cellId was processed in $time ms"
            }
        }

        return result.toList()
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }

}