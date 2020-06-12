package com.chillibits.coronaaid.model.dto

data class InfectedCompressedDto(
        val id: Int,
        val forename: String,
        val surname: String,
        val lat: Double,
        val lon: Double,
        val phone: String?,
        val timestampCallToday: Long?,
        val personalFeeling: Int?,
        val sumInitialDiseases: Int?,
        val sumSymptoms: Int?
)