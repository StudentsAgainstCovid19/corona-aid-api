package com.chillibits.coronaaid.shared

import com.chillibits.coronaaid.model.db.ConfigItem
import com.chillibits.coronaaid.model.db.ContactItem
import com.chillibits.coronaaid.model.db.HistoryItem
import com.chillibits.coronaaid.model.db.Infected
import com.chillibits.coronaaid.model.db.InitialDisease
import com.chillibits.coronaaid.model.db.ResidentialGroup
import com.chillibits.coronaaid.model.db.Symptom
import com.chillibits.coronaaid.model.db.Test
import com.chillibits.coronaaid.model.dto.ConfigItemDto
import com.chillibits.coronaaid.model.dto.ContactItemDto
import com.chillibits.coronaaid.model.dto.HistoryItemDto
import com.chillibits.coronaaid.model.dto.InfectedDto
import com.chillibits.coronaaid.model.dto.InitialDiseaseDto
import com.chillibits.coronaaid.model.dto.ResidentialGroupDto
import com.chillibits.coronaaid.model.dto.SymptomDto
import com.chillibits.coronaaid.model.dto.TestDto

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
        contactData = this.contactData.map { it.toDto() },
        tests = this.tests.map { it.toDto() },
        initialDiseases = this.initialDiseases.map { it.toDto() },
        historyItems = this.historyItems.map { it.toDto() },
        residentialGroups = this.residentialGroups.map { it.toDto() }
)

fun ContactItem.toDto() = ContactItemDto(
        id = this.id,
        contactKey = this.contactKey,
        contactValue = this.contactValue
)

fun Test.toDto() = TestDto(
        id = this.id,
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
        symptom = this.symptoms.map { it.toDto() },
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