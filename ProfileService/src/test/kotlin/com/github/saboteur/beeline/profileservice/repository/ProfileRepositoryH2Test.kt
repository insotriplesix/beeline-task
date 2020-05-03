package com.github.saboteur.beeline.profileservice.repository

import com.github.saboteur.beeline.profileservice.repository.impl.CallerRepositoryImpl
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest(
    classes = [
        CallerRepositoryImpl::class,
        H2Config::class
    ]
)
@ActiveProfiles("test")
class ProfileRepositoryH2Test {

    @Autowired
    lateinit var callerRepository: CallerRepository

    @Test
    fun findCallerIdWhenNoSuchCtnInDatabase() {
        val answer = callerRepository.findCallerIdByCtn("TEST")
        val expected = ""

        Assertions.assertThat(answer).isEqualTo(expected)
    }

    @ParameterizedTest
    @ArgumentsSource(ProfileRepositoryDataProvider.FindCallerId::class)
    fun findCallerIdWhenSuchCtnInDatabase(
        providedCtn: String,
        providedCallerId: String
    ) {
        val answer = callerRepository.findCallerIdByCtn(providedCtn)

        Assertions.assertThat(answer).isEqualTo(providedCallerId)
    }

}