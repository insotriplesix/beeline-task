package com.github.saboteur.beeline.profileservice.controller.impl

import com.github.saboteur.beeline.profileservice.controller.ProfileServiceControllerApi
import com.github.saboteur.beeline.profileservice.dto.ProfileDto
import com.github.saboteur.beeline.profileservice.service.ProfileService
import io.swagger.annotations.Api
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@Api
@RestController
class ProfileServiceControllerImpl(
    private val profileService: ProfileService
) : ProfileServiceControllerApi {

    override suspend fun getProfileByCtn(ctn: String): ResponseEntity<ProfileDto> {
        val profile = profileService.getProfile(ctn)
        return ResponseEntity.ok(profile)
    }

}