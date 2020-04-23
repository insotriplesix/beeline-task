package com.github.saboteur.beeline.profileservice.model

data class AggregatedInfo(
    var ctn: String,
    var callerId: String,
    val gender: String?,
    val titleName: String?,
    val firstName: String?,
    val lastName: String?,
    val email: String?
)