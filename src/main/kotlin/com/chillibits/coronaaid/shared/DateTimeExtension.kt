package com.chillibits.coronaaid.shared

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

fun LocalDate.yearsBetween(other : LocalDate): Long =
        ChronoUnit.YEARS.between(this, other)

fun Instant.truncateToMidnight(): Long =
        this.truncatedTo(ChronoUnit.DAYS).toEpochMilli()

fun Instant.truncateToLocalMidnight(): Long =
        this.atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS).toInstant().toEpochMilli()

fun Instant.zonedEpochMilli(zone: ZoneId = ZoneId.systemDefault()): Long =
        this.atZone(zone).toInstant().toEpochMilli()