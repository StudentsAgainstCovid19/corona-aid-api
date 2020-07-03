package com.chillibits.coronaaid.controller.v1

import com.bedatadriven.jackson.datatype.jts.JtsModule
import com.chillibits.coronaaid.model.dto.DistrictAnalyticsDto
import com.chillibits.coronaaid.model.dto.DistrictDto
import com.chillibits.coronaaid.model.mapper.toDto
import com.chillibits.coronaaid.repository.DistrictRepository
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Api(value = "District REST Endpoint", tags = ["district"])
class DistrictController {

    @Autowired
    private lateinit var districtRepository: DistrictRepository

    @GetMapping(
            path = ["/district"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Returns all districts with all available attributes")
    fun getAllGeoDistricts(): List<DistrictDto> = districtRepository.findAll().map { it.toDto() }

    @GetMapping(
            path = ["/district/analytics"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Returns all districts with some additional analytical data")
    fun getAllGeoDistrictsAnalytics(): Set<DistrictAnalyticsDto> = districtRepository.getAnalyzedDistrictData()

    @Bean
    fun jtsModule(): JtsModule = JtsModule()

}