package com.github.saboteur.beeline.detailservice.model

data class Session(
    var cellId: String,
    var ctn: String
) {
    companion object {
        val empty = Session(
            cellId = "",
            ctn = ""
        )
    }
}