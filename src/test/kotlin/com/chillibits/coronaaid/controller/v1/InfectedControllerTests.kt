package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.exception.exception.InfectedLockedException
import com.chillibits.coronaaid.exception.exception.InfectedNotFoundException
import com.chillibits.coronaaid.model.db.ConfigItem
import com.chillibits.coronaaid.model.db.Infected
import com.chillibits.coronaaid.model.dto.InfectedDto
import com.chillibits.coronaaid.repository.ConfigRepository
import com.chillibits.coronaaid.repository.InfectedRepository
import com.chillibits.coronaaid.shared.ConfigKeys
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertThrows
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Bean
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.time.LocalDate
import java.util.*

@RunWith(SpringRunner::class)
@ActiveProfiles("logging")
@DisplayName("Infected Controller")
class InfectedControllerTests {

    @Autowired
    private lateinit var infectedController: InfectedController
    @MockBean
    private lateinit var infectedRepository: InfectedRepository
    @MockBean
    private lateinit var configRepository: ConfigRepository

    private val testBirthDate = LocalDate.now()
    private val testTimestamp = System.currentTimeMillis()
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
        Mockito.`when`(infectedRepository.findById(testData[0].id)).thenReturn(Optional.of(testData[0]))
        Mockito.`when`(infectedRepository.findById(testData[1].id)).thenReturn(Optional.of(testData[1]))
        Mockito.`when`(infectedRepository.findById(10)).thenReturn(Optional.empty())
        Mockito.`when`(configRepository.findByConfigKey(ConfigKeys.CK_AUTO_RESET_OFFSET))
                .thenReturn(ConfigItem(0, ConfigKeys.CK_AUTO_RESET_OFFSET, ConfigKeys.CK_AUTO_RESET_OFFSET_DEFAULT))
    }

    // ---------------------------------------------------- Tests ------------------------------------------------------

    @Test
    @DisplayName("Test for getting all infected - success")
    fun testGetAllInfected() {
        val result = infectedController.getAllInfected()
        assertThat(result).containsExactlyInAnyOrder(assertData[0], assertData[1])
    }

    @Test
    @DisplayName("Test for getting a single infected, which is unlocked - success")
    fun testGetSingleInfectedUnlocked() {
        val result = infectedController.getSingleInfected(testData[0].id)
        assertEquals(assertData[0], result)
    }

    @Test
    @DisplayName("Test for getting a single infected, which not exists - exception")
    fun testGetSingleInfectedNotFound() {
        assertThrows<InfectedNotFoundException> { infectedController.getSingleInfected(10) }
    }

    @Test
    @DisplayName("Test for getting a single infected, which is locked - exception")
    fun testGetSingleInfectedLocked() {
        assertThrows<InfectedLockedException> { infectedController.getSingleInfected(testData[1].id) }
    }

    @Test
    @DisplayName("Test for locking a single infected manually - successful")
    fun testLockSingleInfected() {
        val timestamp = infectedController.lockSingleInfected(testData[0].id)
        verify(infectedRepository).changeLockedState(testData[0].id, timestamp)
    }

    @Test
    @DisplayName("Test for locking a single infected manually - infected not found")
    fun testLockSingleInfectedNotFound() {
        assertThrows<InfectedNotFoundException> { infectedController.lockSingleInfected(10) }
    }

    @Test
    @DisplayName("Test for unlocking a single infected manually - successful")
    fun testUnlockSingleInfected() {
        infectedController.unlockSingleInfected(testData[1].id)
        verify(infectedRepository).changeLockedState(testData[1].id, 0)
    }

    @Test
    @DisplayName("Test for unlocking a single infected manually - infected not found")
    fun testUnlockSingleInfectedNotFound() {
        assertThrows<InfectedNotFoundException> { infectedController.unlockSingleInfected(10) }
    }

    // -------------------------------------------------- Test data ----------------------------------------------------

    private fun getTestData(): List<Infected> {
        val infected1 = Infected(0, "John", "Doe", testBirthDate, "Karlsruhe", "76131",
                        "Erzbergerstraße", "121", 49.0264134, 8.3831085, healthInsuranceNumber = "M123456",
                        lockedTimestamp = 0)
        val infected2 = Infected(1, "Joe", "Dalton", testBirthDate, "Mannheim", "76131",
                        "Göthestraße", "4", 49.4874639, 8.4763718,  "M654321",
                        lockedTimestamp = testTimestamp)
        return listOf(infected1, infected2)
    }

    private fun getAssertData(): List<InfectedDto> {
        val infected1 = InfectedDto(0, "John", "Doe", testBirthDate, "Karlsruhe", "76131",
                        "Erzbergerstraße", "121", 49.0264134, 8.3831085,  "M123456",
                         emptyList(), emptyList(), emptyList(), emptyList(), emptyList())
        val infected2 = InfectedDto(1, "Joe", "Dalton", testBirthDate, "Mannheim", "76131",
                        "Göthestraße", "4", 49.4874639, 8.4763718, "M654321",
                        emptyList(), emptyList(), emptyList(), emptyList(), emptyList())
        return listOf(infected1, infected2)
    }
}