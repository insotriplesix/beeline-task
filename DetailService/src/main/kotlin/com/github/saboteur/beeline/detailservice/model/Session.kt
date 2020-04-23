package com.github.saboteur.beeline.detailservice.model

data class Session(
    val cellId: String,
    val ctn: String
) {
    companion object {
        val empty = Session(
            cellId = "",
            ctn = ""
        )
    }
}