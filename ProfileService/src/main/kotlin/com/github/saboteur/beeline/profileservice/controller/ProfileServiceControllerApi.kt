package com.github.saboteur.beeline.profileservice.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api(value = "Profile Service", description = "Profile service controller")
@RestController
@RequestMapping("api")
interface ProfileServiceControllerApi {

    @ApiOperation(value = "Get caller id by given ctn number")
    @GetMapping("/$API_VERSION/profileservice/getCallerIdByCtn")
    fun getCallerIdByCtn(
        @ApiParam("CTN (Cellular Telephone Number) - a mobile number", example = "1234567890")
        @RequestParam(value = "ctn", required = true)
        ctn: String
    ): ResponseEntity<String>

    companion object {
        const val API_VERSION = "v1"
    }

}