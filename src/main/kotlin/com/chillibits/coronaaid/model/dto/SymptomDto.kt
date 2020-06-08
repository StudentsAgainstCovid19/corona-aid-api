package com.chillibits.coronaaid.model.dto

data class SymptomDto (
        val id: Int,
        val name: String,
        val degreeOfDanger: Int,
        val probability: Int
)