package com.chillibits.coronaaid.model.dto

import com.chillibits.coronaaid.shared.yearsBetween
import java.time.LocalDate

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
        val healthInsuranceNumber: String,
        val contactData: Set<ContactItemDto>,
        val tests: Set<TestDto>,
        val initialDiseases: Set<InitialDiseaseDto>,
        val historyItems: Set<HistoryItemDto>,
        val residentialGroups: Set<ResidentialGroupDto>
) {
        val age = this.birthDate.yearsBetween(LocalDate.now())
}