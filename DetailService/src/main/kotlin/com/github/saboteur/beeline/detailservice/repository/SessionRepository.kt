package com.github.saboteur.beeline.detailservice.repository

interface SessionRepository {
    fun findAllCtnByCellId(cellId: String): List<String>
}