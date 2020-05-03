package com.github.saboteur.beeline.detailservice.repository

import com.github.saboteur.beeline.detailservice.model.Session
import com.github.saboteur.beeline.detailservice.repository.impl.SessionRepositoryImpl
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.lang.Exception

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class DetailRepositoryTest {

    @MockK
    lateinit var jdbcTemplate: JdbcTemplate

    @InjectMockKs
    lateinit var sessionRepository: SessionRepositoryImpl

    @BeforeAll
    fun setUp() {
        // setup stub
    }

    @AfterAll
    fun cleanUp() {
        clearAllMocks()
    }

    @Test
    fun testSessionModel() {
        val session = Session(
            cellId = "12345",
            ctn = "1234567890"
        )

        with (session) {
            Assertions.assertThat(cellId).isEqualTo("12345")
            Assertions.assertThat(ctn).isEqualTo("1234567890")
        }
    }

    @ParameterizedTest
    @ArgumentsSource(DetailRepositoryDataProvider.FindAllCtnsException::class)
    fun findAllCtnsWhenExceptionThrown(
        providedSql: String,
        providedRowMapper: RowMapper<Session>
    ) {
        every {
            jdbcTemplate.query(providedSql, providedRowMapper)
        } throws Exception("")

        val answer = sessionRepository.findAllCtnsByCellId("TEST")
        val expected = emptyList<String>()

        Assertions.assertThat(answer).isEqualTo(expected)
    }

}