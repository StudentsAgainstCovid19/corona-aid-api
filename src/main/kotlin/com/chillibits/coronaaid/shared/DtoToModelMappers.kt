package com.chillibits.coronaaid.shared

import com.chillibits.coronaaid.model.db.ContactItem
import com.chillibits.coronaaid.model.db.HistoryItem
import com.chillibits.coronaaid.model.db.Infected
import com.chillibits.coronaaid.model.db.InitialDisease
import com.chillibits.coronaaid.model.db.ResidentialGroup
import com.chillibits.coronaaid.model.db.Test
import com.chillibits.coronaaid.model.dto.ContactItemDto
import com.chillibits.coronaaid.model.dto.HistoryItemDto
import com.chillibits.coronaaid.model.dto.InfectedDto
import com.chillibits.coronaaid.model.dto.InitialDiseaseDto
import com.chillibits.coronaaid.model.dto.ResidentialGroupDto
import com.chillibits.coronaaid.model.dto.TestDto

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
        contactData = this.contactData.map { it.toModel() },
        tests = this.tests.map { it.toModel() },
        initialDiseases = this.initialDiseases.map { it.toModel() },
        historyItems = this.historyItems.map { it.toModel() },
        residentialGroups = this.residentialGroups.map { it.toModel() },
        locked = false,
        lockedLastUpdate = System.currentTimeMillis()
)
fun ContactItemDto.toModel() = ContactItem(
        id = this.id,
        contactKey = this.contactKey,
        contactValue = this.contactValue
)
fun InitialDiseaseDto.toModel() = InitialDisease(
        id = this.id,
        degreeOfDanger = this.degreeOfDanger
)

fun HistoryItemDto.toModel() = HistoryItem(
        id = this.id,
        timestamp = this.timestamp,
        symptoms = emptyList(),
        status = this.status,
        personalFeeling = this.personalFeeling
)

fun ResidentialGroupDto.toModel() = ResidentialGroup(
        id = this.id,
        infected = emptyList()
)

fun TestDto.toModel() = Test(
        id = this.id,
        timestamp = this.timestamp,
        result = this.result
)