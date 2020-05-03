package com.github.saboteur.beeline.profileservice.repository.impl

import com.github.saboteur.beeline.profileservice.model.Caller
import com.github.saboteur.beeline.profileservice.repository.CallerRepository
import mu.KotlinLogging
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import kotlin.system.measureTimeMillis

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
            var result: Caller? = null

            measureTimeMillis {
                result = template.queryForObject(sql, rowMapper)
            }.also { time ->
                logger.debug { "Query < $sql > took $time ms" }
            }

            result?.callerId ?: ""
        } catch (e: Exception) {
            logger.error { "Data fetch error for CTN = $ctn, error: ${e.localizedMessage}" }
            ""
        }

    companion object {
        private val logger = KotlinLogging.logger {}
    }

}