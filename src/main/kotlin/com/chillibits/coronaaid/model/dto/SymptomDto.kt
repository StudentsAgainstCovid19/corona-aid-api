package com.chillibits.coronaaid.model.dto

import com.fasterxml.jackson.annotation.JsonBackReference

data class SymptomDto (
        val id: Int,
        @JsonBackReference
        val historyItems: List<HistoryItemDto>? = null,
        val name: String,
        val degreeOfDanger: Int,
        val probability: Int
)