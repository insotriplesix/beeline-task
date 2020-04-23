package com.github.saboteur.beeline.detailservice.repository

interface SessionRepository {
    fun findAllCtnByCid(cid: String): List<String>
}