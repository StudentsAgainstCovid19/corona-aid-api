package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.exception.exception.InfectedNotFoundException
import com.chillibits.coronaaid.model.db.HistoryItem
import com.chillibits.coronaaid.model.db.Infected
import com.chillibits.coronaaid.model.db.Symptom
import com.chillibits.coronaaid.model.dto.HistoryItemDto
import com.chillibits.coronaaid.model.dto.HistoryItemInsertDto
import com.chillibits.coronaaid.model.dto.SymptomDto
import com.chillibits.coronaaid.repository.HistoryRepository
import com.chillibits.coronaaid.repository.InfectedRepository
import com.chillibits.coronaaid.repository.SymptomRepository
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate
import java.time.Month
import java.util.*

@RunWith(SpringRunner::class)
@ActiveProfiles("logging")
@DisplayName("Config Controller")
class HistoryControllerTests {

    @Autowired
    private lateinit var historyController: HistoryController

    @MockBean
    private lateinit var historyRepository: HistoryRepository

    @MockBean
    private lateinit var symptomRepository: SymptomRepository

    @MockBean
    private lateinit var infectedRepository: InfectedRepository

    private val dummyInfected = getDummyInfected()
    private val testData = getHistoryTestData()
    private val assertData = getHistoryAssertData()

    @TestConfiguration
    class HistoryControllerImplTestContextConfiguration {

        @Bean
        fun historyController() = HistoryController()

    }

    @Before
    fun init() {
        //history repository
        Mockito.`when`(historyRepository.findAll()).thenReturn(testData)
        Mockito.`when`(historyRepository.getHistoryItemsForPerson(5)).thenReturn(listOf(testData[0]))
        Mockito.`when`(historyRepository.save(getPostRequiredRepositorySaveInput())).thenReturn(getPostRepositorySaveOutput())

        //symptom repository
        Mockito.`when`(symptomRepository.findById(0)).thenReturn(Optional.of(getPostSymptomTestData()[0]))
        Mockito.`when`(symptomRepository.findAllById(listOf(0))).thenReturn(getPostSymptomTestData())

        //infected repository
        Mockito.`when`(infectedRepository.findById(5)).thenReturn(Optional.of(getDummyInfected()))
    }

    // ---------------------------------------------------- Tests ------------------------------------------------------

    @Test
    fun testGetAllHistoryItems() {
        Assertions.assertThat(historyController.getAllHistoryItems()).containsExactlyInAnyOrder(assertData[0], assertData[1])
    }

    @Test
    fun testGetHistoryItemsOfSinglePerson() {
        Assertions.assertThat(historyController.getHistoryItemForSinglePerson(5)).isEqualTo(listOf(assertData[0]))
    }

    @Test
    fun testPostSingleHistoryItem() {
        val result = historyController.addHistoryItem(getPostInputDto()) //simulate client request
        Assertions.assertThat(result).isEqualTo(ResponseEntity(getPostExpectedPostOutputDto(), HttpStatus.CREATED))
    }

    @Test
    fun testPostSingleHistoryItemWithUnknownInfected() {
        assertThrows<InfectedNotFoundException>({ historyController.addHistoryItem(getPostUnknownInfectedDto()) })
    }

    // -------------------------------------------------- Test data ----------------------------------------------------

    private fun getDummyInfected()
            = Infected(5, "Donald", "Trump", LocalDate.of(1900, Month.JANUARY, 10),
            "Pjöngjang", "kim-111", "Nuclearstreet", "666", 420.0, 360.0,
            healthInsuranceNumber = "M123456", lockedTimestamp = 99999L)

    private fun getHistoryTestData(): List<HistoryItem> {
        val historyItem1 = HistoryItem(0, getDummyInfected(), 1234, emptyList(), 0, 1)
        val historyItem2 = HistoryItem(1, null, 4321, emptyList(), 1, 0)
        return listOf(historyItem1, historyItem2)
    }

    private fun getHistoryAssertData(): List<HistoryItemDto> {
        val historyItem1 = HistoryItemDto(0, getDummyInfected().id, 1234, emptyList(), 0, 1)
        val historyItem2 = HistoryItemDto(1, null, 4321, emptyList(), 1, 0)
        return listOf(historyItem1, historyItem2)
    }

    private fun getPostSymptomTestData() = listOf(Symptom(0, emptyList(), "fever", 7, 80))
    private fun getPostSymptomAssertData() = listOf(SymptomDto(0, "fever", 7, 80))

    private fun getPostInputDto()
            = HistoryItemInsertDto(getDummyInfected().id, 9999, listOf(0), 2, 5)
    private fun getPostRequiredRepositorySaveInput()
            = HistoryItem(0, getDummyInfected(), 9999, listOf(getPostSymptomTestData()[0]), 2, 5)
    private fun getPostRepositorySaveOutput()
            = HistoryItem(100, getDummyInfected(), 9999, listOf(getPostSymptomTestData()[0]), 2, 5)
    private fun getPostExpectedPostOutputDto()
            = HistoryItemDto(100, getDummyInfected().id, 9999, listOf(getPostSymptomAssertData()[0]), 2, 5)
    private fun getPostUnknownInfectedDto()
            = HistoryItemInsertDto(919191, 9999, listOf(0), 2, 5)
}