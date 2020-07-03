package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.model.db.District
import com.chillibits.coronaaid.model.dto.DistrictAnalyticsDto
import com.chillibits.coronaaid.model.dto.DistrictDto
import com.chillibits.coronaaid.repository.DistrictRepository
import com.vividsolutions.jts.geom.*
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

@RunWith(SpringRunner::class)
@ActiveProfiles("logging")
@DisplayName("Geo Controller")
class DistrictControllerTests {

    @Autowired
    private lateinit var districtController: DistrictController

    @MockBean
    private lateinit var districtRepository: DistrictRepository

    private val geometryFactory = GeometryFactory()

    @TestConfiguration
    class DistrictControllerImplTestContextConfiguration {

        @Bean
        fun districtController() = DistrictController()
    }

    @Before
    fun init() {
        // District Repository
        Mockito.`when`(districtRepository.findAll()).thenReturn(getDistrictTestData())
        Mockito.`when`(districtRepository.getAnalyzedDistrictData()).thenReturn(getDistrictAnalyticsData())
    }

    @Test
    fun testGetAllDistricts() {
        assertThat(districtController.getAllGeoDistricts()).containsExactlyInAnyOrderElementsOf(getDistrictAssertData())
    }

    @Test
    fun testgetAllDistrictsAnalytics() {
        assertThat(districtController.getAllGeoDistrictsAnalytics()).containsExactlyInAnyOrderElementsOf(getDistrictAnalyticsData())
    }

    fun getDistrictTestData(): List<District> {
        val district1 = District(0, "Hohenwettersbach", "Karlsruhe", "76228",
                                    geometryFactory.createPolygon(emptyArray()))
        val district2 = District(0, "Grünwettersbach", "Karlsruhe", "76228",
                                    geometryFactory.createPolygon(emptyArray()))

        return listOf(district1, district2)
    }

    fun getDistrictAssertData(): Set<DistrictDto> {
        val district1 = DistrictDto(0, "Hohenwettersbach", "Karlsruhe", "76228",
                                    geometryFactory.createPolygon(emptyArray()))
        val district2 = DistrictDto(0, "Grünwettersbach", "Karlsruhe", "76228",
                                    geometryFactory.createPolygon(emptyArray()))

        return setOf(district1, district2)
    }

    fun getDistrictAnalyticsData(): Set<DistrictAnalyticsDto> {
        val district1 = DistrictAnalyticsDto(0, "Hohenwettersbach", "Karlsruhe", 12.32, "76228", 4,
                                                geometryFactory.createPolygon(emptyArray()))
        val district2 = DistrictAnalyticsDto(0, "Grünwettersbach", "Karlsruhe", 99.91,"76228", 12,
                                                geometryFactory.createPolygon(emptyArray()))

        return setOf(district1, district2)
    }

}