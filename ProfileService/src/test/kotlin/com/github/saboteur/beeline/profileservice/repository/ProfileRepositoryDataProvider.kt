package com.github.saboteur.beeline.profileservice.repository

import com.github.saboteur.beeline.profileservice.model.Caller
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import org.springframework.jdbc.core.RowMapper
import java.util.stream.Stream

class ProfileRepositoryDataProvider {

    class FindCallerId : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext?): Stream<out Arguments> {
            val providedCtn = "1234567890"
            val providedCallerId = "03e17537-30de-4598-a816-108945fa68b4"

            return Stream.of(Arguments.of(providedCtn, providedCallerId))
        }

    }

    class FindCallerIdException : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext?): Stream<out Arguments> {
            val providedSql = "SELECT ctn, caller_id FROM callers"
            val providedRowMapper = RowMapper { rs, _ ->
                Caller(rs.getString("ctn"), rs.getString("caller_id"))
            }

            return Stream.of(Arguments.of(providedSql, providedRowMapper))
        }

    }

}