package com.github.saboteur.beeline.profileservice.dto.external.randomuser

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class RandomUserResultDto(
    val gender: String?,
    val name: RandomUserNameDto?,
    val email: String?
)