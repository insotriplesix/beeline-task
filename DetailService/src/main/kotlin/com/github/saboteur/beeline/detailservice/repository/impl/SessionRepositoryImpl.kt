package com.github.saboteur.beeline.detailservice.repository.impl

import com.github.saboteur.beeline.detailservice.model.Session
import com.github.saboteur.beeline.detailservice.repository.SessionRepository
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class SessionRepositoryImpl(
    private val template: JdbcTemplate
) : SessionRepository {

    override fun findAllCtnByCid(cid: String): List<String> =
        try {
            val rowMapper = RowMapper { rs, _ ->
                Session(rs.getString("cell_id"), rs.getString("ctn"))
            }
            val sql = "SELECT cell_id, ctn FROM sessions WHERE cell_id = '$cid'"
            val result = template
                .query(
                    sql,
                    rowMapper
                )
                .asSequence()
                .map {
                    it.ctn
                }
                .toList()
            result
        } catch (e: Exception) {
            logger.error(e) { "Data fetch error for CID = $cid" }
            emptyList()
        }

    companion object {
        private val logger = KotlinLogging.logger {}
    }

}