package com.chillibits.coronaaid.model.dto

import com.chillibits.coronaaid.model.db.HistoryItem
import com.chillibits.coronaaid.model.db.InitialDisease
import com.chillibits.coronaaid.model.db.ResidentialGroup
import com.chillibits.coronaaid.model.db.Test
import java.util.*

class InfectedDto (
        val id: Int = 0,
        val forename: String = "",
        val surname: String = "",
        val birthDate: Date = Date(),
        val city: String = "",
        val postalCode: Int = 0,
        val street: String = "",
        val houseNumber: Int = 0,
        val lat: Double = 0.0,
        val lon: Double = 0.0,
        val contactData: List<ContactItemDto> = emptyList(),
        val tests: List<Test> = emptyList(),
        val initialDiseases: List<InitialDisease> = emptyList(),
        val historyItems: List<HistoryItem> = emptyList(),
        val residentialGroups: List<ResidentialGroup> = emptyList()
)