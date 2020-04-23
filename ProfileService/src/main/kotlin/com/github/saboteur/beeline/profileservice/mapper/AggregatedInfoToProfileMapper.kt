package com.github.saboteur.beeline.profileservice.mapper

import com.github.saboteur.beeline.profileservice.dto.ProfileDto
import com.github.saboteur.beeline.profileservice.model.AggregatedInfo

object AggregatedInfoToProfileMapper : Mapper<AggregatedInfo, ProfileDto> {

    override fun get(from: AggregatedInfo): ProfileDto =
        ProfileDto(
            ctn = from.ctn,
            callerId = from.callerId,
            name = from.firstName ?: "" + from.lastName ?: "",
            email = from.email ?: ""
        )

}