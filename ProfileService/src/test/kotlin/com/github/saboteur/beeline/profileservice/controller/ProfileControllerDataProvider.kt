package com.github.saboteur.beeline.profileservice.controller

import com.github.saboteur.beeline.profileservice.dto.ProfileDto
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

class ProfileControllerDataProvider {

    class GetProfile : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext?): Stream<out Arguments> {
            val providedProfile = ProfileDto(
                ctn = "1234567890",
                callerId = "03e17537-30de-4598-a816-108945fa68b4",
                name = "Pavel Durov",
                email = "durov@hotmail.com"
            )

            return Stream.of(Arguments.of(providedProfile))
        }
    }

}