package com.chillibits.coronaaid.model.dto

data class HistoryItemDto (
        val id: Int,
        val timestamp: Long,
        val symptom: List<SymptomDto>,
        val status: Int,
        val personalFeeling: Int
)