package com.chillibits.coronaaid.shared

import com.chillibits.coronaaid.model.db.*
import com.chillibits.coronaaid.model.dto.*
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.ChronoUnit

fun LocalDate.yearsBetween(other : LocalDate): Long =
        ChronoUnit.YEARS.between(this, other)
fun Instant.truncateToMidnight(): Long =
        this.truncatedTo(ChronoUnit.DAYS).toEpochMilli()
fun Instant.truncateToLocalMidnight(): Long =
        this.atZone(ZoneId.systemDefault()).truncatedTo(ChronoUnit.DAYS).toInstant().toEpochMilli()
fun Instant.zonedEpochMilli(zone: ZoneId = ZoneId.systemDefault()): Long =
        this.atZone(zone).toInstant().toEpochMilli()

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
        done = this.done,
        contactData = this.contactData.map { it.toDto() }.toSet(),
        tests = this.tests.map { it.toDto() }.toSet(),
        initialDiseases = this.initialDiseases.map { it.toDto() }.toSet(),
        historyItems = this.historyItems.map { it.toDto() }.toSet()
)

fun Infected.toCompressed(configAutoResetOffset : Long): InfectedCompressedDto {
    //Introduce local variable to prevent redundant function call of Infected::historyItems::sortedByDescending
    val sortedHistory = this.historyItems.sortedByDescending { it.timestamp }
    val lastSuccessfulCall = sortedHistory.filter { it.status == HistoryItem.STATUS_REACHED }.firstOrNull()

    val latestMidnight = Instant.now().truncateToLocalMidnight()
    val todayUnsuccessfulTimestamp = sortedHistory.filter { Instant.ofEpochMilli(it.timestamp).zonedEpochMilli() >= latestMidnight && it.status == HistoryItem.STATUS_NOT_REACHABLE }.map { it.timestamp }.firstOrNull()
    
    return InfectedCompressedDto(
            id = this.id,
            age = this.birthDate.yearsBetween(LocalDate.now()),
            forename = this.forename,
            surname = this.surname,
            lat = this.lat,
            lon = this.lon,
            phone = this.contactData.filter { it.contactKey.equals("phone") }.map { it.contactValue }.firstOrNull(),
            locked = this.lockedTimestamp > System.currentTimeMillis() - configAutoResetOffset,
            done = this.done,
            lastUnsuccessfulCallToday = todayUnsuccessfulTimestamp,
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

fun HistoryItem.toDto() = HistoryItemDto(
        id = this.id,
        infectedId = this.infectedId?.id,
        timestamp = this.timestamp,
        symptoms = this.symptoms.map { it.toDto() }.toSet(),
        status = this.status,
        personalFeeling = this.personalFeeling,
        notes = this.notes
)

fun Symptom.toDto() = SymptomDto(
        id = this.id,
        name = this.name,
        degreeOfDanger = this.degreeOfDanger,
        probability = this.probability
)

fun Disease.toDto() = DiseaseDto(
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