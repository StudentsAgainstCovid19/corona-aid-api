package com.chillibits.coronaaid.shared

import com.chillibits.coronaaid.model.db.ConfigItem
import com.chillibits.coronaaid.model.db.ContactItem
import com.chillibits.coronaaid.model.db.HistoryItem
import com.chillibits.coronaaid.model.db.Infected
import com.chillibits.coronaaid.model.db.InitialDisease
import com.chillibits.coronaaid.model.db.ResidentialGroup
import com.chillibits.coronaaid.model.db.Symptom
import com.chillibits.coronaaid.model.db.Test
import com.chillibits.coronaaid.model.dto.*
import java.time.Instant
import java.time.temporal.ChronoUnit

fun Infected.toDto() = InfectedDto(
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
        contactData = this.contactData.map { it.toDto() }.toSet(),
        tests = this.tests.map { it.toDto() }.toSet(),
        initialDiseases = this.initialDiseases.map { it.toDto() }.toSet(),
        historyItems = this.historyItems.map { it.toDto() }.toSet(),
        residentialGroups = this.residentialGroups.map { it.toDto() }.toSet()
)

fun Infected.toCompressed(): InfectedCompressedDto {
    //Introduce local variable to prevent redundant function call of Infected::historyItems::sortedByDescending
    val sortedHistory = this.historyItems.sortedByDescending { it.timestamp }
    val lastSuccessfulCall = sortedHistory.filter { it.status == HistoryItem.STATUS_REACHED }.firstOrNull()

    val latestMidnight = Instant.now().truncatedTo(ChronoUnit.DAYS).toEpochMilli()
    val todayTimestamp = sortedHistory.filter { it.timestamp >= latestMidnight && it.status == HistoryItem.STATUS_REACHED }.map { it.timestamp }.firstOrNull()

    return InfectedCompressedDto(
            id = this.id,
            forename = this.forename,
            surname = this.surname,
            lat = this.lat,
            lon = this.lon,
            phone = this.contactData.filter { it.contactKey.equals("phone") }.map { it.contactValue }.firstOrNull(),
            timestampCallToday = todayTimestamp,
            personalFeeling = lastSuccessfulCall?.personalFeeling,
            sumInitialDiseases = this.initialDiseases.size,
            sumSymptoms = lastSuccessfulCall?.symptoms?.size
    )
}

fun ContactItem.toDto() = ContactItemDto(
        id = this.id,
        contactKey = this.contactKey,
        contactValue = this.contactValue
)

fun Test.toDto() = TestDto(
        id = this.id,
        infectedId = this.infectedId?.id,
        timestamp = this.timestamp,
        result = this.result
)

fun InitialDisease.toDto() = InitialDiseaseDto(
        id = this.id,
        degreeOfDanger = this.degreeOfDanger
)

fun HistoryItem.toDto() = HistoryItemDto(
        id = this.id,
        infectedId = this.infectedId?.id,
        timestamp = this.timestamp,
        symptoms = this.symptoms.map { it.toDto() },
        status = this.status,
        personalFeeling = this.personalFeeling,
        notes = this.notes
)

fun ResidentialGroup.toDto() = ResidentialGroupDto(
        id = this.id
)

fun Symptom.toDto() = SymptomDto(
        id = this.id,
        name = this.name,
        degreeOfDanger = this.degreeOfDanger,
        probability = this.probability
)

fun ConfigItem.toDto() = ConfigItemDto(
        id = this.id,
        configKey = this.configKey,
        configValue = this.configValue
)