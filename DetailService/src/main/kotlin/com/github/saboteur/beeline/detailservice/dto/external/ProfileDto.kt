package com.github.saboteur.beeline.detailservice.dto.external

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ProfileDto(
    val ctn: String,
    val callerId: String,
    val name: String,
    val email: String
)