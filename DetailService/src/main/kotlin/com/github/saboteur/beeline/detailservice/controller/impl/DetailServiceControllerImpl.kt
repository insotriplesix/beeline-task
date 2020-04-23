package com.github.saboteur.beeline.detailservice.controller.impl

import com.github.saboteur.beeline.detailservice.controller.DetailServiceControllerApi
import com.github.saboteur.beeline.detailservice.dto.CallerProfile
import com.github.saboteur.beeline.detailservice.service.DetailService
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.web.bind.annotation.RestController

@Api
@RestController
@EnableAsync
class DetailServiceControllerImpl(
    private val detailService: DetailService
) : DetailServiceControllerApi {

    override fun getCallerProfileByCid(cid: String): ResponseEntity<List<CallerProfile>> {
        val profiles = detailService.getCallerProfile(cid)
        return ResponseEntity
            .status(
                if (profiles.isEmpty())
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(profiles)
    }

}