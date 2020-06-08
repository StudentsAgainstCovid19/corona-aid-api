package com.chillibits.coronaaid.model.dto

data class HistoryItemInsertDto(
        val infectedId : Int,
        val timestamp: Long,
        val symptom: List<Int>,
        val status: Int,
        val personalFeeling: Int
)