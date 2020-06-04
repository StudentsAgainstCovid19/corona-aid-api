package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.model.db.ConfigItem
import com.chillibits.coronaaid.model.dto.ConfigItemDto
import com.chillibits.coronaaid.repository.ConfigRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertEquals
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
@DisplayName("Config Controller")
class ConfigControllerTests {

    @Autowired
    private lateinit var configController: ConfigController
    @MockBean
    private lateinit var configRepository: ConfigRepository

    private val testData = getTestData()
    private val assertData = getAssertData()

    @TestConfiguration
    class ConfigControllerImplTestContextConfiguration {

        @Bean
        fun configController() = ConfigController()
    }

    @Before
    fun init() {
        // Setup fake function calls
        Mockito.`when`(configRepository.findAll()).thenReturn(testData)
        Mockito.`when`(configRepository.findByConfigKey(testData[0].configKey)).thenReturn(testData[0])
    }

    // ---------------------------------------------------- Tests ------------------------------------------------------

    @Test
    @DisplayName("Test for getting all config properties - success")
    fun testGetAllConfigItems() {
        val result = configController.getAllConfigItems()
        assertThat(result).containsExactlyInAnyOrder(assertData[0], assertData[1])
    }

    @Test
    @DisplayName("Test for getting single config property - success")
    fun getSingleConfigItem() {
        val result = configController.getSingleConfigItem(testData[0].configKey)
        assertEquals(assertData[0], result)
    }

    // -------------------------------------------------- Test data ----------------------------------------------------

    private fun getTestData(): List<ConfigItem> {
        val item1 = ConfigItem(0, "priorityAgeWeight", "1.57")
        val item2 = ConfigItem(0, "priorityDiseaseWeight", "2.31")
        return listOf(item1, item2)
    }

    private fun getAssertData(): List<ConfigItemDto> {
        val item1 = ConfigItemDto(0, "priorityAgeWeight", "1.57")
        val item2 = ConfigItemDto(0, "priorityDiseaseWeight", "2.31")
        return listOf(item1, item2)
    }
}