package com.chillibits.coronaaid.model.dto

import com.fasterxml.jackson.annotation.JsonBackReference

data class ContactItemDto (
        val id: Int,
        @JsonBackReference
        val infectedId: InfectedDto? = null,
        val contactKey: String,
        val contactValue: String
)