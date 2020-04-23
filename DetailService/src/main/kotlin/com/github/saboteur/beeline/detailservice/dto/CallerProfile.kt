package com.github.saboteur.beeline.detailservice.dto

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel(value = "CallerProfile")
data class CallerProfile(

    @ApiModelProperty(value = "ctn", required = true)
    val ctn: String,

    @ApiModelProperty(value = "cid", required = true)
    val cid: String,

    @ApiModelProperty(value = "name")
    val name: String,

    @ApiModelProperty(value = "email")
    val email: String

) {
    companion object {
        val empty = CallerProfile(
            ctn = "",
            cid = "",
            name = "",
            email = ""
        )
    }
}