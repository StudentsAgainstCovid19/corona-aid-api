package com.chillibits.coronaaid.model.dto

data class HistoryItemUpdateDto(
        val historyItemId: Int,
        val infectedId: Int,
        val timestamp: Long,
        val symptoms: Set<Int>?,
        val status: Int,
        val personalFeeling: Int,
        val notes: String?
)