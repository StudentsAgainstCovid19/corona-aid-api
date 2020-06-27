package com.chillibits.coronaaid.model.dto

import com.chillibits.coronaaid.shared.DateTimeConverters
import java.time.Instant
import java.time.LocalTime
import java.time.ZoneId

data class InfectedCompressedDto(
        val id: Int,
        val age : Long,
        val forename: String,
        val surname: String,
        val lat: Double,
        val lon: Double,
        val phone: String?,
        val done : Boolean,
        val locked : Boolean,
        val lastUnsuccessfulCallToday: Long?,
        val personalFeeling: Int?,
        val sumInitialDiseases: Int?,
        val sumSymptoms: Int?
) {
    var lastUnsuccessfulCallTodayString : String? = lastUnsuccessfulCallToday?.let {
        LocalTime.ofInstant(Instant.ofEpochMilli(lastUnsuccessfulCallToday), ZoneId.systemDefault()).format(DateTimeConverters.TIME_HOURS_MINUTES)
    }
}