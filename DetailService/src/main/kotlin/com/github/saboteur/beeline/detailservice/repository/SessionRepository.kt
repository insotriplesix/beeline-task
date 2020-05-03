package com.github.saboteur.beeline.detailservice.repository

interface SessionRepository {
    fun findAllCtnsByCellId(cellId: String): List<String>
}