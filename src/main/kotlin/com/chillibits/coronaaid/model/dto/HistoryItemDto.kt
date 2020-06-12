package com.chillibits.coronaaid.model.dto

data class HistoryItemDto (
        val id: Int,
        val infectedId : Int?,
        val timestamp: Long,
        val symptoms: List<SymptomDto>,
        val status: Int,
        val personalFeeling: Int,
        val notes: String?
)