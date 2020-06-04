package com.chillibits.coronaaid.model.dto

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference

data class HistoryItemDto (
        val id: Int,
        @JsonBackReference
        val infectedId: InfectedDto? = null,
        val timestamp: Long,
        @JsonManagedReference
        val symptom: List<SymptomDto>,
        val status: Int,
        val personalFeeling: Int
)