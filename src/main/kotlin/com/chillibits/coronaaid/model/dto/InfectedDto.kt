package com.chillibits.coronaaid.model.dto

import java.time.LocalDate
import java.time.temporal.ChronoUnit

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
        val notes : String?,
        val contactData: List<ContactItemDto>,
        val tests: List<TestDto>,
        val initialDiseases: List<InitialDiseaseDto>,
        val historyItems: List<HistoryItemDto>,
        val residentialGroups: List<ResidentialGroupDto>
) {
        val age = ChronoUnit.YEARS.between(this.birthDate, LocalDate.now())
}