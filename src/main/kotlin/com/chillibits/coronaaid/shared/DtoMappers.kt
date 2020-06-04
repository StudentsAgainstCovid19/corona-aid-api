package com.chillibits.coronaaid.shared

import com.chillibits.coronaaid.model.db.ConfigItem
import com.chillibits.coronaaid.model.db.ContactItem
import com.chillibits.coronaaid.model.db.Infected
import com.chillibits.coronaaid.model.dto.ConfigItemDto
import com.chillibits.coronaaid.model.dto.ContactItemDto
import com.chillibits.coronaaid.model.dto.InfectedDto

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
        contactData = this.contactData.map { it.toDto() },
        tests = this.tests,
        initialDiseases = this.initialDiseases,
        historyItems = this.historyItems,
        residentialGroups = this.residentialGroups
)

fun ContactItem.toDto() = ContactItemDto(
        id = this.id,
        infectedId = this.infectedId,
        contactKey = this.contactKey,
        contactValue = this.contactValue
)

fun ConfigItem.toDto() = ConfigItemDto(
        id = this.id,
        configKey = this.configKey,
        configValue = this.configValue
)