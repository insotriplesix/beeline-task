package com.github.saboteur.beeline.profileservice.repository

interface CallerRepository {
    fun findCallerIdByCtn(ctn: String): String
}