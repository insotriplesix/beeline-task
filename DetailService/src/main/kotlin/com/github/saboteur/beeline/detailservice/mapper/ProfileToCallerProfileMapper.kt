package com.github.saboteur.beeline.detailservice.mapper

import com.github.saboteur.beeline.detailservice.dto.CallerProfile
import com.github.saboteur.beeline.detailservice.dto.external.ProfileDto

object ProfileToCallerProfileMapper : Mapper<ProfileDto, CallerProfile> {

    override fun get(from: ProfileDto): CallerProfile =
        CallerProfile(
            ctn = from.ctn,
            callerId = from.callerId,
            name = from.name,
            email = from.email
        )

}