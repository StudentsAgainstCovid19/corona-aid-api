package com.chillibits.coronaaid.model.dto

import java.time.Instant
import java.time.temporal.ChronoUnit

data class TestDto (
        val id: Int,
        val infectedId : Int?,
        val timestamp: Long,
        val result: Int
) {
    val daysOverdue: Long = ChronoUnit.DAYS.between(Instant.ofEpochMilli(timestamp), Instant.now())
}