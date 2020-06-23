package com.chillibits.coronaaid.model.dto

data class DiseaseDto(
        val id : Int,
        val name : String,
        val degreeOfDanger: Int,
        val probability: Int
)