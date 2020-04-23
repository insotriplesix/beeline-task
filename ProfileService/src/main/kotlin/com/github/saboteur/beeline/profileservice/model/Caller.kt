package com.github.saboteur.beeline.profileservice.model

data class Caller(
    val ctn: String,
    val callerId: String
) {
    companion object {
        val empty = Caller(
            ctn = "",
            callerId = ""
        )
    }
}