package com.chillibits.coronaaid.model.dto

import com.chillibits.coronaaid.model.db.HistoryItem
import com.chillibits.coronaaid.model.db.InitialDisease
import com.chillibits.coronaaid.model.db.ResidentialGroup
import com.chillibits.coronaaid.model.db.Test
import com.fasterxml.jackson.annotation.JsonManagedReference
import java.util.*

data class InfectedDto (
        val id: Int,
        val forename: String,
        val surname: String,
        val birthDate: Date,
        val city: String,
        val postalCode: Int,
        val street: String,
        val houseNumber: Int,
        val lat: Double,
        val lon: Double,
        @JsonManagedReference
        val contactData: List<ContactItemDto>,
        @JsonManagedReference
        val tests: List<Test>,
        @JsonManagedReference
        val initialDiseases: List<InitialDisease>,
        @JsonManagedReference
        val historyItems: List<HistoryItem>,
        @JsonManagedReference
        val residentialGroups: List<ResidentialGroup>
)