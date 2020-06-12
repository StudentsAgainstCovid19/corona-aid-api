package com.chillibits.coronaaid.model.dto

data class HistoryItemInsertDto(
        val infectedId : Int,
        val timestamp: Long,
        val symptoms: List<Int>?,
        val status: Int,
        val personalFeeling: Int,
        val notes : String?
)