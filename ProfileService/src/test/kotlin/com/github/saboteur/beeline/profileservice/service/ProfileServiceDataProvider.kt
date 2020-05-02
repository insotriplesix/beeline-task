package com.github.saboteur.beeline.profileservice.service

import com.github.saboteur.beeline.profileservice.dto.ProfileDto
import com.github.saboteur.beeline.profileservice.dto.external.randomuser.RandomUserNameDto
import com.github.saboteur.beeline.profileservice.dto.external.randomuser.RandomUserProfileDto
import com.github.saboteur.beeline.profileservice.dto.external.randomuser.RandomUserResultDto
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

class ProfileServiceDataProvider {

    class GetProfileWithoutCallerId : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext?): Stream<out Arguments> {
            val providedProfile = ProfileDto(
                ctn = "TEST",
                callerId = "",
                name = "Durov Super",
                email = "sdurov@hotmail.com"
            )

            val providedRandomUserProfileDto = RandomUserProfileDto(
                results = listOf(
                    RandomUserResultDto(
                        gender = null,
                        name = RandomUserNameDto(
                            title = "Mr.",
                            first = "Super",
                            last = "Durov"
                        ),
                        email = "sdurov@hotmail.com"
                    )
                ),
                info = null,
                error = null
            )

            return Stream.of(Arguments.of(providedProfile, providedRandomUserProfileDto))
        }
    }

    class GetProfileWithCallerId : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext?): Stream<out Arguments> {
            val providedProfile = ProfileDto(
                ctn = "1234567890",
                callerId = "03e17537-30de-4598-a816-108945fa68b4",
                name = "Durov Pavel",
                email = "pdurov@hotmail.com"
            )

            val providedRandomUserProfileDto = RandomUserProfileDto(
                results = listOf(
                    RandomUserResultDto(
                        gender = "M",
                        name = RandomUserNameDto(
                            title = "Mr.",
                            first = "Pavel",
                            last = "Durov"
                        ),
                        email = "pdurov@hotmail.com"
                    )
                ),
                info = null,
                error = null
            )

            val providedCallerId = "03e17537-30de-4598-a816-108945fa68b4"

            return Stream.of(Arguments.of(providedProfile, providedRandomUserProfileDto, providedCallerId))
        }
    }

    class GetProfileWithError : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext?): Stream<out Arguments> {
            val providedProfile = ProfileDto(
                ctn = "1234567890",
                callerId = "03e17537-30de-4598-a816-108945fa68b4",
                name = "",
                email = ""
            )

            val providedRandomUserProfileDto = RandomUserProfileDto(
                results = null,
                info = null,
                error = "This is error! Error?! THIS IS TEST CASE!!!!111"
            )

            val providedCallerId = "03e17537-30de-4598-a816-108945fa68b4"

            return Stream.of(Arguments.of(providedProfile, providedRandomUserProfileDto, providedCallerId))
        }
    }

}