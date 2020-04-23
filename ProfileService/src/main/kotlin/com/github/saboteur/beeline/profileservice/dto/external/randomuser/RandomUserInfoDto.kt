package com.github.saboteur.beeline.profileservice.dto.external.randomuser

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class RandomUserInfoDto(
    val seed: String?,
    val results: Int?,
    val page: Int?,
    val version: String?
)