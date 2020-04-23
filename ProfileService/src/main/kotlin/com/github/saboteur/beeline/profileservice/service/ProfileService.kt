package com.github.saboteur.beeline.profileservice.service

import com.github.saboteur.beeline.profileservice.repository.CallerRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class ProfileService(
    private val sessionRepository: CallerRepository
) {

    fun getCallerId(ctn: String): String {
        val callerId = sessionRepository.findCallerIdByCtn(ctn)

        // @TODO: send to kafka instead
        logger.info { callerId }

        return callerId
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }

}