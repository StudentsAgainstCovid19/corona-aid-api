package com.chillibits.coronaaid.model.dto

import com.vividsolutions.jts.geom.Geometry

data class DistrictAnalyticsDto(
        val name: String,
        val city: String,
        val area: Double,
        val postalCode: String,
        val infected: Long,
        val geometry: Geometry
)