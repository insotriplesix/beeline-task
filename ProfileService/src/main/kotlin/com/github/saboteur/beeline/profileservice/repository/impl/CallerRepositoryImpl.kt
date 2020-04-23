package com.github.saboteur.beeline.profileservice.repository.impl

import com.github.saboteur.beeline.profileservice.model.Caller
import com.github.saboteur.beeline.profileservice.repository.CallerRepository
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class CallerRepositoryImpl(
    private val template: JdbcTemplate
) : CallerRepository {

    override fun findCallerIdByCtn(ctn: String): String =
        try {
            val rowMapper = RowMapper { rs, _ ->
                Caller(rs.getString("ctn"), rs.getString("caller_id"))
            }
            val sql = "SELECT ctn, caller_id FROM callers WHERE ctn = '$ctn'"
            val result = template.queryForObject(sql, rowMapper)
            result?.callerId ?: ""
        } catch (e: Exception) {
            logger.error(e) { "Data fetch error for CTN = $ctn" }
            ""
        }

    companion object {
        private val logger = KotlinLogging.logger {}
    }

}