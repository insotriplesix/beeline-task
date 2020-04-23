package com.github.saboteur.beeline.detailservice.service

import com.github.saboteur.beeline.detailservice.dto.CallerProfile
import com.github.saboteur.beeline.detailservice.repository.SessionRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class DetailService(
    private val sessionRepository: SessionRepository
) {

    fun getCallerProfile(cid: String): List<CallerProfile> {
        val ctns = sessionRepository.findAllCtnByCid(cid)

        // @TODO: send to kafka instead
        logger.info { "$ctns" }

        return listOf(CallerProfile.empty)
    }

    companion object {
        private val logger = KotlinLogging.logger {}
    }

}