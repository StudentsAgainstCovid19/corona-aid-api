package com.chillibits.coronaaid.model.dto

import com.fasterxml.jackson.annotation.JsonManagedReference
import java.time.LocalDate
import java.util.*

data class InfectedDto (
        val id: Int,
        val forename: String,
        val surname: String,
        val birthDate: LocalDate,
        val city: String,
        val postalCode: String,
        val street: String,
        val houseNumber: String,
        val lat: Double,
        val lon: Double,
        @JsonManagedReference
        val contactData: List<ContactItemDto>,
        @JsonManagedReference
        val tests: List<TestDto>,
        @JsonManagedReference
        val initialDiseases: List<InitialDiseaseDto>,
        @JsonManagedReference
        val historyItems: List<HistoryItemDto>,
        @JsonManagedReference
        val residentialGroups: List<ResidentialGroupDto>
)