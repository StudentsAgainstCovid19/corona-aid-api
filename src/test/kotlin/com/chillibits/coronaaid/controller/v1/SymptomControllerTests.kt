package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.model.db.Symptom
import com.chillibits.coronaaid.model.dto.SymptomDto
import com.chillibits.coronaaid.repository.SymptomRepository
import org.assertj.core.api.Assertions
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

@RunWith(SpringRunner::class)
@ActiveProfiles("logging")
@DisplayName("Symptom Controller")
class SymptomControllerTests {

    @Autowired
    private lateinit var symptomController: SymptomController

    @MockBean
    private lateinit var symptomRepository: SymptomRepository

    private val testData = getTestData()
    private val assertData = getAssertData()

    @TestConfiguration
    class SymptomControllerImplTestContextConfiguration {

        @Bean
        fun symptomController() = SymptomController()
    }

    @Before
    fun init() {
        Mockito.`when`(symptomRepository.findAll()).thenReturn(testData)
    }

    // ---------------------------------------------------- Tests ------------------------------------------------------

    @Test
    @DisplayName("Test for getting all symptoms")
    fun testGetAllSymptoms() {
        val result = symptomController.getAllSymptoms()
        Assertions.assertThat(result).containsExactlyInAnyOrder(assertData[0], assertData[1])
    }

    // -------------------------------------------------- Test data ----------------------------------------------------

    private fun getTestData(): List<Symptom> {
        val symptom1 = Symptom(0, emptyList(), "fever", 7, 80)
        val symptom2 = Symptom(1, emptyList(), "fatigue", 2, 100)
        return listOf(symptom1, symptom2)
    }

    private fun getAssertData(): List<SymptomDto> {
        val symptom1 = SymptomDto(0,"fever", 7, 80)
        val symptom2 = SymptomDto(1,"fatigue", 2, 100)
        return listOf(symptom1, symptom2)
    }
}