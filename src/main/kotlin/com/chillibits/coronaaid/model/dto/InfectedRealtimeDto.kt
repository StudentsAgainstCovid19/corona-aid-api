package com.chillibits.coronaaid.model.dto

data class InfectedRealtimeDto(
        val infectedId: Int,
        val done: Boolean,
        val called: Boolean,
        val lastUnsuccessfulCallTodayString : String?,
        val locked: Boolean
)