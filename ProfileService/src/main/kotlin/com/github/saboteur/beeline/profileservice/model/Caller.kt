package com.github.saboteur.beeline.profileservice.model

data class Caller(
    var ctn: String,
    var callerId: String
) {
    companion object {
        val empty = Caller(
            ctn = "",
            callerId = ""
        )
    }
}