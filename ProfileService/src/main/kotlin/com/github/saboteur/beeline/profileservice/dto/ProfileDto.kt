package com.github.saboteur.beeline.profileservice.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ProfileDto(
    val ctn: String,
    val callerId: String,
    val name: String,
    val email: String
) {
    companion object {
        val empty = ProfileDto(
            ctn = "",
            callerId = "",
            name = "",
            email = ""
        )
    }
}