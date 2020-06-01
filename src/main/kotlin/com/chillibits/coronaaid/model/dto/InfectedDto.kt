package com.chillibits.coronaaid.model.dto

import com.chillibits.coronaaid.model.db.ContactItem
import com.chillibits.coronaaid.model.db.HistoryItem
import com.chillibits.coronaaid.model.db.InitialDisease
import com.chillibits.coronaaid.model.db.ResidentialGroup
import com.chillibits.coronaaid.model.db.Test
import java.util.*

class InfectedDto (
        private val id: Int = 0,
        private val forename: String = "",
        private val surname: String = "",
        private val birthDate: Date = Date(),
        private val city: String = "",
        private val postalCode: Int = 0,
        private val street: String = "",
        private val houseNumber: Int = 0,
        private val lat: Double = 0.0,
        private val lon: Double = 0.0,
        private val contactData: List<ContactItem> = emptyList(),
        private val tests: List<Test> = emptyList(),
        private val initialDiseases: List<InitialDisease> = emptyList(),
        private val historyItems: List<HistoryItem> = emptyList(),
        private val residentialGroups: List<ResidentialGroup> = emptyList()
)