package com.chillibits.coronaaid.model.mapper

import com.chillibits.coronaaid.model.db.*
import com.chillibits.coronaaid.model.dto.*

fun InfectedDto.toModel() = Infected(
        id = this.id,
        forename = this.forename,
        surname = this.surname,
        birthDate = this.birthDate,
        city = this.city,
        postalCode = this.postalCode,
        street = this.street,
        houseNumber = this.houseNumber,
        lat = this.lat,
        lon = this.lon,
        healthInsuranceNumber = this.healthInsuranceNumber,
        contactData = this.contactData.map { it.toModel() }.toSet(),
        tests = this.tests.map { it.toModel() }.toSet(),
        initialDiseases = this.initialDiseases.map { it.toModel() }.toSet(),
        historyItems = this.historyItems.map { it.toModel() }.toSet(),
        lockedTimestamp = System.currentTimeMillis()
)

fun ContactItemDto.toModel() = ContactItem(
        id = this.id,
        contactKey = this.contactKey,
        contactValue = this.contactValue
)

fun HistoryItemDto.toModel() = HistoryItem(
        id = this.id,
        timestamp = this.timestamp,
        symptoms = emptySet(),
        status = this.status,
        personalFeeling = this.personalFeeling
)

fun DiseaseDto.toModel() = Disease(
        id = this.id,
        degreeOfDanger = this.degreeOfDanger,
        probability = this.probability,
        name = this.name,
        initialDiseases = emptySet()
)

fun TestDto.toModel() = Test(
        id = this.id,
        timestamp = this.timestamp,
        result = this.result
)