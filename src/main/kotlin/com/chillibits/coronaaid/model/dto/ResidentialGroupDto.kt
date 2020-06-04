package com.chillibits.coronaaid.model.dto

import com.fasterxml.jackson.annotation.JsonBackReference

data class ResidentialGroupDto (
        val id: Int,
        @JsonBackReference
        val infected: List<InfectedDto>? = null
)