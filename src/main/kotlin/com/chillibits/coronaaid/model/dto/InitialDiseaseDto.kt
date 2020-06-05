package com.chillibits.coronaaid.model.dto

import com.fasterxml.jackson.annotation.JsonBackReference

data class InitialDiseaseDto (
        val id: Int,
        @JsonBackReference
        val infectedId: InfectedDto? = null,
        val degreeOfDanger: Int
)