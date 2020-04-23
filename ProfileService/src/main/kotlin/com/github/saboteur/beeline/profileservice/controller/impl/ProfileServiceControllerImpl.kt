package com.github.saboteur.beeline.profileservice.controller.impl

import com.github.saboteur.beeline.profileservice.controller.ProfileServiceControllerApi
import com.github.saboteur.beeline.profileservice.service.ProfileService
import io.swagger.annotations.Api
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.web.bind.annotation.RestController

@Api
@RestController
@EnableAsync
class ProfileServiceControllerImpl(
    private val detailService: ProfileService
) : ProfileServiceControllerApi {

    override fun getCallerIdByCtn(ctn: String): ResponseEntity<String> {
        val callerId = detailService.getCallerId(ctn)
        return ResponseEntity
            .status(
                if (callerId.isEmpty() || callerId.isBlank())
                    HttpStatus.NOT_FOUND
                else
                    HttpStatus.OK
            )
            .body(callerId)
    }

}