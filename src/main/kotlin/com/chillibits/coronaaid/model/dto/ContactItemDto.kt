package com.chillibits.coronaaid.model.dto

import com.chillibits.coronaaid.model.db.Infected

class ContactItemDto (
        val id: Int = 0,
        val infectedId: Infected? = null,
        val contactKey: String = "",
        val contactValue: String = ""
)