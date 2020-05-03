package com.github.saboteur.beeline.detailservice.repository

import com.github.saboteur.beeline.detailservice.repository.impl.SessionRepositoryImpl
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
        SessionRepositoryImpl::class,
        H2Config::class
    ]
)
@ActiveProfiles("test")
class DetailRepositoryTest {

    @Autowired
    lateinit var sessionRepository: SessionRepository

    @Test
    fun findCtnsWhenNoSuchCellIdInDatabase() {
        val answer = sessionRepository.findAllCtnsByCellId("TEST")
        val expected = emptyList<String>()

        Assertions.assertThat(answer).isEqualTo(expected)
    }

    @ParameterizedTest
    @ArgumentsSource(DetailRepositoryDataProvider.FindAllCtns::class)
    fun findCtnsWhenSuchCellIdInDatabase(
        providedCellId: String,
        providedCtns: List<String>
    ) {
        val answer = sessionRepository.findAllCtnsByCellId(providedCellId)

        Assertions.assertThat(answer).containsAll(providedCtns)
    }

}