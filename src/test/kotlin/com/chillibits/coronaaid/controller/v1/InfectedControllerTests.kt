package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.model.db.Infected
import com.chillibits.coronaaid.model.dto.InfectedDto
import com.chillibits.coronaaid.repository.InfectedRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@ActiveProfiles("logging")
@DisplayName("Infected Controller")
class InfectedControllerTests {

    @Autowired
    private lateinit var infectedController: InfectedController
    @MockBean
    private lateinit var infectedRepository: InfectedRepository

    private val testBirthDate = Date()
    private val testData = getTestData()
    private val assertData = getAssertData()

    @TestConfiguration
    class InfectedControllerImplTestContextConfiguration {

        @Bean
        fun infectedController() = InfectedController()
    }

    @Before
    fun init() {
        // Setup fake function calls
        Mockito.`when`(infectedRepository.findAll()).thenReturn(testData)
    }

    // ---------------------------------------------------- Tests ------------------------------------------------------

    @Test
    @DisplayName("Test for getting all infected - success")
    fun testGetAllInfected() {
        val result = infectedController.getAllInfected()
        assertThat(result).containsExactlyInAnyOrder(assertData[0], assertData[1])
    }

    // -------------------------------------------------- Test data ----------------------------------------------------

    private fun getTestData(): List<Infected> {
        val infected1 = Infected(0, "John", "Doe", testBirthDate, "Karlsruhe", 76131, "Erzbergerstraße", 121, 49.0264134, 8.3831085)
        val infected2 = Infected(1, "Joe", "Dalton", testBirthDate, "Karlsruhe", 76131, "Göthestraße", 4, 49.0091689, 8.3807658)
        return listOf(infected1, infected2)
    }

    private fun getAssertData(): List<InfectedDto> {
        val infected1 = InfectedDto(0, "John", "Doe", testBirthDate, "Karlsruhe", 76131, "Erzbergerstraße", 121, 49.0264134, 8.3831085, emptyList(), emptyList(), emptyList(), emptyList(), emptyList())
        val infected2 = InfectedDto(1, "Joe", "Dalton", testBirthDate, "Karlsruhe", 76131, "Göthestraße", 4, 49.0091689, 8.3807658, emptyList(), emptyList(), emptyList(), emptyList(), emptyList())
        return listOf(infected1, infected2)
    }
}