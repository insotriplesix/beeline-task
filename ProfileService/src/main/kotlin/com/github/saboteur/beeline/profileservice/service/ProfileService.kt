package com.github.saboteur.beeline.profileservice.service

import com.github.saboteur.beeline.profileservice.dto.ProfileDto

interface ProfileService {
    fun getProfile(ctn: String): ProfileDto
}