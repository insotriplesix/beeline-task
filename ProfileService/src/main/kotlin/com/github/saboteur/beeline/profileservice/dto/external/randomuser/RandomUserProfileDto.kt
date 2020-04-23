package com.github.saboteur.beeline.profileservice.dto.external.randomuser

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class RandomUserProfileDto(
    val results: List<RandomUserResultDto>?,
    val info: RandomUserInfoDto?,
    val error: String?
)