package com.chillibits.coronaaid.model.dto

import org.locationtech.jts.geom.Geometry

data class DistrictAnalyticsDto(
        val id : Int,
        val name: String,
        val city: String,
        val area: Double,
        val postalCode: String,
        val infected: Long,
        val geometry: Geometry
)