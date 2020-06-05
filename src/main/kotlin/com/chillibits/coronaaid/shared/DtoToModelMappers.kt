package com.chillibits.coronaaid.shared

/*fun InfectedDto.toModel() = Infected(
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
        residentialGroups = this.residentialGroups.map { it.toModel() }
)
fun ContactItemDto.toModel() = ContactItem(
        id = this.id,
        infectedId = null,
        contactKey = this.contactKey,
        contactValue = this.contactValue
)
fun InitialDiseaseDto.toModel() = InitialDisease(
        id = this.id,
        infectedId = this.infectedId
)
fun TestDto.toModel() = Test(
        id = this.id,
        infectedId = this.infectedId.toModel(),
        timestamp = this.timestamp,
        result = this.result
)*/