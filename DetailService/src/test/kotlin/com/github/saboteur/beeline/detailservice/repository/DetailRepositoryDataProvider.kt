package com.github.saboteur.beeline.detailservice.repository

import com.github.saboteur.beeline.detailservice.model.Session
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.springframework.jdbc.core.RowMapper
import java.util.stream.Stream

class DetailRepositoryDataProvider {

    class FindAllCtns : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext?): Stream<out Arguments> {
            val providedCellId = "11111"
            val providedCtns = listOf(
                "1234567890",
                "1234567891",
                "1234567898"
            )

            return Stream.of(Arguments.of(providedCellId, providedCtns))
        }

    }

    class FindAllCtnsException : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext?): Stream<out Arguments> {
            val providedSql = "SELECT cell_id, ctn FROM sessions"
            val providedRowMapper = RowMapper { rs, _ ->
                Session(rs.getString("cell_id"), rs.getString("ctn"))
            }

            return Stream.of(Arguments.of(providedSql, providedRowMapper))
        }

    }

}