package com.chillibits.coronaaid.model.dto

import com.vividsolutions.jts.geom.Geometry

data class DistrictDto(
        val id: Int,
        val name: String,
        val city: String,
        val postalCode: String,
        val geometry: Geometry
)