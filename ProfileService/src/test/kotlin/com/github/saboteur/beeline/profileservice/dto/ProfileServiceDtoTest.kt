package com.github.saboteur.beeline.profileservice.dto

import com.github.saboteur.beeline.profileservice.dto.external.randomuser.RandomUserInfoDto
import com.github.saboteur.beeline.profileservice.dto.external.randomuser.RandomUserNameDto
import com.github.saboteur.beeline.profileservice.dto.external.randomuser.RandomUserProfileDto
import com.github.saboteur.beeline.profileservice.dto.external.randomuser.RandomUserResultDto
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ProfileServiceDtoTest {

    @Test
    fun testRandomUserDtos() {
        val nameDto = RandomUserNameDto(
            title = "Mr.",
            first = "nobody",
            last = null
        )

        val resultDto = RandomUserResultDto(
            gender = "M",
            name = nameDto,
            email = null
        )

        val infoDto = RandomUserInfoDto(
            seed = "seed",
            results = 1,
            page = 1,
            version = "1"
        )

        val profileDto = RandomUserProfileDto(
            results = listOf(resultDto),
            info = infoDto,
            error = null
        )

        with (profileDto) {
            Assertions.assertThat(results).isNotNull
            Assertions.assertThat(results!!.size).isEqualTo(1)
            Assertions.assertThat(info).isNotNull
            Assertions.assertThat(error).isNull()
        }

        with (profileDto.results!![0]) {
            Assertions.assertThat(gender).isEqualTo("M")
            Assertions.assertThat(name).isNotNull
            Assertions.assertThat(email).isNull()
        }

        with (profileDto.results!![0].name!!) {
            Assertions.assertThat(title).isEqualTo("Mr.")
            Assertions.assertThat(first).isEqualTo("nobody")
            Assertions.assertThat(last).isNull()
        }

        with (profileDto.info!!) {
            Assertions.assertThat(seed).isEqualTo("seed")
            Assertions.assertThat(results).isEqualTo(1)
            Assertions.assertThat(page).isEqualTo(1)
            Assertions.assertThat(version).isEqualTo("1")
        }
    }

}