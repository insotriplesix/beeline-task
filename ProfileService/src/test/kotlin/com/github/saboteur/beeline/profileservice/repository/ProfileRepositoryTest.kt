package com.github.saboteur.beeline.profileservice.repository

import com.github.saboteur.beeline.profileservice.model.AggregatedInfo
import com.github.saboteur.beeline.profileservice.model.Caller
import com.github.saboteur.beeline.profileservice.repository.impl.CallerRepositoryImpl
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ArgumentsSource
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import java.lang.Exception

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
class ProfileRepositoryTest {

    @MockK
    lateinit var jdbcTemplate: JdbcTemplate

    @InjectMockKs
    lateinit var callerRepository: CallerRepositoryImpl

    @BeforeAll
    fun setUp() {
        // setup stub
    }

    @AfterAll
    fun cleanUp() {
        clearAllMocks()
    }

    @ParameterizedTest
    @ArgumentsSource(ProfileRepositoryDataProvider.FindCallerId::class)
    fun testCallerModel(
        providedCtn: String,
        providedCallerId: String
    ) {
        val caller = Caller(
            ctn = providedCtn,
            callerId = providedCallerId
        )

        with (caller) {
            Assertions.assertThat(ctn).isEqualTo("1234567890")
            Assertions.assertThat(callerId).isEqualTo("03e17537-30de-4598-a816-108945fa68b4")
        }
    }

    @ParameterizedTest
    @ArgumentsSource(ProfileRepositoryDataProvider.FindCallerId::class)
    fun testAggregatedInfo(
        providedCtn: String,
        providedCallerId: String
    ) {
        val aggregatedInfo = AggregatedInfo(
            ctn = providedCtn,
            callerId = providedCallerId,
            gender = null,
            titleName = null,
            firstName = "Pavel",
            lastName = "Durov",
            email = "pdurov@hotmail.com"
        )

        with (aggregatedInfo) {
            Assertions.assertThat(ctn).isEqualTo("1234567890")
            Assertions.assertThat(callerId).isEqualTo("03e17537-30de-4598-a816-108945fa68b4")
            Assertions.assertThat(gender).isNull()
            Assertions.assertThat(titleName).isNull()
            Assertions.assertThat(firstName).isEqualTo("Pavel")
            Assertions.assertThat(lastName).isEqualTo("Durov")
            Assertions.assertThat(email).isEqualTo("pdurov@hotmail.com")
        }
    }

    @ParameterizedTest
    @ArgumentsSource(ProfileRepositoryDataProvider.FindCallerIdException::class)
    fun findCallerIdWhenExceptionThrown(
        providedSql: String,
        providedRowMapper: RowMapper<Caller>
    ) {
        every {
            jdbcTemplate.queryForObject(providedSql, providedRowMapper)
        } throws Exception("")

        val answer = callerRepository.findCallerIdByCtn("TEST")
        val expected = ""

        Assertions.assertThat(answer).isEqualTo(expected)
    }

    @ParameterizedTest
    @ArgumentsSource(ProfileRepositoryDataProvider.FindCallerIdException::class)
    fun findCallerIdWhenResponseIsNull(
        providedSql: String,
        providedRowMapper: RowMapper<Caller>
    ) {
        every {
            jdbcTemplate.queryForObject(providedSql, providedRowMapper)
        } returns null

        val answer = callerRepository.findCallerIdByCtn("TEST")
        val expected = ""

        Assertions.assertThat(answer).isEqualTo(expected)
    }

}