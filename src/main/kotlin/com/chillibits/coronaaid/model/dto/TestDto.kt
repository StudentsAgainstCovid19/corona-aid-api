package com.chillibits.coronaaid.model.dto

import com.fasterxml.jackson.annotation.JsonBackReference

data class TestDto (
        val id: Int,
        @JsonBackReference
        val infectedId: InfectedDto? = null,
        val timestamp: Long,
        val result: Int
)