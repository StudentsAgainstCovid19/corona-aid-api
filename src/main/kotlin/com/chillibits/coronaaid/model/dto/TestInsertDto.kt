package com.chillibits.coronaaid.model.dto

data class TestInsertDto(
        val infectedId : Int,
        val timestamp: Long,
        val result: Int
)