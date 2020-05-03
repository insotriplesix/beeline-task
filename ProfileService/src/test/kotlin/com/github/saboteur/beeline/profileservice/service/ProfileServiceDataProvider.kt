package com.github.saboteur.beeline.profileservice.service

import com.github.saboteur.beeline.profileservice.config.properties.RestProperties
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

            val providedRestProperties = RestProperties(
                externalServiceUrl = "externalServiceUrl",
                externalServiceName = "randomuser",
                fields = "name,email"
            )

            val providedReqUrl =
                StringBuilder()
                    .append(providedRestProperties.externalServiceUrl)
                    .append("/api/?phone=")
                    .append("TEST")
                    .append("&inc=")
                    .append(providedRestProperties.fields)
                    .toString()

            val arguments = Arguments.of(
                providedProfile,
                providedRandomUserProfileDto,
                providedRestProperties,
                providedReqUrl
            )

            return Stream.of(arguments)
        }
    }

    class GetProfileWithCallerId : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext?): Stream<out Arguments> {
            val providedProfile = ProfileDto(
                ctn = "1234567890",
                callerId = exampleId,
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

            val providedCallerId = exampleId

            val providedRestProperties = RestProperties(
                externalServiceUrl = "externalServiceUrl",
                externalServiceName = "randomuser",
                fields = "name,email"
            )

            val providedReqUrl =
                StringBuilder()
                    .append(providedRestProperties.externalServiceUrl)
                    .append("/api/?phone=")
                    .append("1234567890")
                    .append("&inc=")
                    .append(providedRestProperties.fields)
                    .toString()

            val arguments = Arguments.of(
                providedProfile,
                providedRandomUserProfileDto,
                providedCallerId,
                providedRestProperties,
                providedReqUrl
            )

            return Stream.of(arguments)
        }
    }

    class GetProfileWithError : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext?): Stream<out Arguments> {
            val providedProfile = ProfileDto(
                ctn = "1234567890",
                callerId = exampleId,
                name = "",
                email = ""
            )

            val providedRandomUserProfileDto = RandomUserProfileDto(
                results = null,
                info = null,
                error = "This is error! Error?! THIS IS TEST CASE!!!!111"
            )

            val providedCallerId = exampleId

            val providedRestProperties = RestProperties(
                externalServiceUrl = "externalServiceUrl",
                externalServiceName = "randomuser",
                fields = "name,email"
            )

            val providedReqUrl =
                StringBuilder()
                    .append(providedRestProperties.externalServiceUrl)
                    .append("/api/?phone=")
                    .append("1234567890")
                    .append("&inc=")
                    .append(providedRestProperties.fields)
                    .toString()

            val arguments = Arguments.of(
                providedProfile,
                providedRandomUserProfileDto,
                providedCallerId,
                providedRestProperties,
                providedReqUrl
            )

            return Stream.of(arguments)
        }
    }

    class GetProfileWithUnknownType : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext?): Stream<out Arguments> {
            val providedProfile = ProfileDto(
                ctn = "TEST",
                callerId = "",
                name = "",
                email = ""
            )

            return Stream.of(Arguments.of(providedProfile))
        }
    }

    class GetProfileWhenExceptionThrown : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext?): Stream<out Arguments> {
            val providedProfile = ProfileDto(
                ctn = "TEST",
                callerId = "",
                name = "",
                email = ""
            )

            val providedRestProperties = RestProperties(
                externalServiceUrl = "invalidUrl",
                externalServiceName = "randomuser",
                fields = "gender"
            )

            val providedReqUrl =
                StringBuilder()
                    .append(providedRestProperties.externalServiceUrl)
                    .append("/api/?phone=")
                    .append("TEST")
                    .append("&inc=")
                    .append(providedRestProperties.fields)
                    .toString()

            return Stream.of(Arguments.of(providedProfile, providedRestProperties, providedReqUrl))
        }
    }

    companion object {
        const val exampleId = "03e17537-30de-4598-a816-108945fa68b4"
    }

}