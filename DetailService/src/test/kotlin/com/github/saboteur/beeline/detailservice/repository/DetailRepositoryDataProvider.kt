package com.github.saboteur.beeline.detailservice.repository

import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
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

}