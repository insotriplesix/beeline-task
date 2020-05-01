package com.github.saboteur.beeline.detailservice.service

import com.github.saboteur.beeline.detailservice.dto.CallerProfile

interface DetailService {
    fun getCallerProfile(cellId: String): List<CallerProfile>
}