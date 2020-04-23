package com.github.saboteur.beeline.detailservice.controller

import com.github.saboteur.beeline.detailservice.dto.CallerProfile
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api(value = "Detail Service", description = "Detail service controller")
@RestController
@RequestMapping("api")
interface DetailServiceControllerApi {

    @ApiOperation(value = "Get caller profiles by 'cid' number")
    @GetMapping("/$API_VERSION/detailservice/getCallerProfileByCid")
    fun getCallerProfileByCid(
        @ApiParam("CID (Cell ID) - base station number", example = "12345")
        @RequestParam(value = "cid", required = true)
        cid: String
    ): ResponseEntity<List<CallerProfile>>

    companion object {
        const val API_VERSION = "v1"
    }

}