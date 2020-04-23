package com.github.saboteur.beeline.profileservice.dto.external.randomuser

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class RandomUserNameDto(
    val title: String?,
    val first: String?,
    val last: String?
)