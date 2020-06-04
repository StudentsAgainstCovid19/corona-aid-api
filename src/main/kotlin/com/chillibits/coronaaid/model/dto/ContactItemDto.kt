package com.chillibits.coronaaid.model.dto

import com.chillibits.coronaaid.model.db.Infected
import com.fasterxml.jackson.annotation.JsonBackReference

data class ContactItemDto (
        val id: Int = 0,
        @JsonBackReference
        val infectedId: Infected? = null,
        val contactKey: String = "",
        val contactValue: String = ""
)