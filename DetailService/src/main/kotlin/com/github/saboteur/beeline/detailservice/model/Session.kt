package com.github.saboteur.beeline.detailservice.model

data class Session(
    var cid: String,
    var ctn: String
) {
    companion object {
        val empty = Session(
            cid = "",
            ctn = ""
        )
    }
}