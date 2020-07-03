package com.chillibits.coronaaid.controller.v1

import com.bedatadriven.jackson.datatype.jts.JtsModule
import com.chillibits.coronaaid.model.dto.DistrictAnalyticsDto
import com.chillibits.coronaaid.model.dto.DistrictDto
import com.chillibits.coronaaid.model.mapper.toDto
import com.chillibits.coronaaid.repository.DistrictRepository
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "Geodata REST Endpoint", tags = ["geo"])
class GeoController {

    @Autowired
    private lateinit var districtRepository: DistrictRepository

    @GetMapping(
            path = ["/geo"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    fun getAllGeoDistricts(): List<DistrictDto> = districtRepository.findAll().map { it.toDto() }

    @GetMapping(
            path = ["/geo/analytics"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    fun getAllGeoDistrictsAnalytics(): List<DistrictAnalyticsDto> = districtRepository.getAnalyzedDistrictData()

    @Bean
    fun jtsModule(): JtsModule = JtsModule()

}