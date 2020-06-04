package com.chillibits.coronaaid.model.dto

import com.fasterxml.jackson.annotation.JsonBackReference

data class InitialDiseaseDto (
        val id: Int,
        @JsonBackReference
        val infectedId: InfectedDto? = null

        // TODO: Wait for response regarding the individual dod
)