package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.model.dto.DistrictAnalyticsDto
import com.chillibits.coronaaid.model.dto.DistrictDto
import com.chillibits.coronaaid.model.mapper.toDto
import com.chillibits.coronaaid.repository.DistrictRepository
import com.chillibits.coronaaid.shared.XmlDtdUrl
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
    @XmlDtdUrl(url = "https://www.corona-aid-ka.de/dtd/district.dtd", rootElement = "Set")
    fun getAllGeoDistricts(): Set<DistrictDto> = districtRepository.findAll().map { it.toDto() }.toSet()

    @GetMapping(
            path = ["/district/analytics"],
            produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE]
    )
    @ApiOperation("Returns all districts with some additional analytical data")
    @XmlDtdUrl(url = "https://www.corona-aid-ka.de/dtd/district_analytics.dtd", rootElement = "Set")
    fun getAllGeoDistrictsAnalytics(): Set<DistrictAnalyticsDto> = districtRepository.getAnalyzedDistrictData()
}