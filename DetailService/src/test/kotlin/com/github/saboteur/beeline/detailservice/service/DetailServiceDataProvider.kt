package com.github.saboteur.beeline.detailservice.service

import com.github.saboteur.beeline.detailservice.dto.CallerProfile
import com.github.saboteur.beeline.detailservice.dto.external.ProfileDto
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.ArgumentsProvider
import java.util.stream.Stream

class DetailServiceDataProvider {

    class GetCallerProfile : ArgumentsProvider {
        override fun provideArguments(extensionContext: ExtensionContext?): Stream<out Arguments> {
            val providedCallerProfiles = listOf(
                CallerProfile(
                    ctn = "1234567890",
                    callerId = "03e17537-30de-4598-a816-108945fa68b4",
                    name = "Durov Pavel",
                    email = "pdurov@hotmail.com"
                ),
                CallerProfile(
                    ctn = "1234567891",
                    callerId = "3ad35c07-12ff-42de-a36d-e4cc21e9c500",
                    name = "Durov Nikolai",
                    email = "ndurov@hotmail.com"
                )
            )

            val providedProfileDtos = listOf(
                ProfileDto(
                    ctn = "1234567890",
                    callerId = "03e17537-30de-4598-a816-108945fa68b4",
                    name = "Durov Pavel",
                    email = "pdurov@hotmail.com"
                ),
                ProfileDto(
                    ctn = "1234567891",
                    callerId = "3ad35c07-12ff-42de-a36d-e4cc21e9c500",
                    name = "Durov Nikolai",
                    email = "ndurov@hotmail.com"
                )
            )

            val providedCtns = listOf("1234567890", "1234567891")
            val providedServiceUrl = "http://localhost:8941/api/v1/profileservice"

            val providedReqUrls = listOf(
                StringBuilder()
                    .append(providedServiceUrl)
                    .append("/getProfileByCtn?ctn=${providedCtns[0]}")
                    .toString(),
                StringBuilder()
                    .append(providedServiceUrl)
                    .append("/getProfileByCtn?ctn=${providedCtns[1]}")
                    .toString()
            )

            val arguments =
                Arguments.of(
                    providedCallerProfiles,
                    providedProfileDtos,
                    providedCtns,
                    providedServiceUrl,
                    providedReqUrls
                )

            return Stream.of(arguments)
        }
    }

}