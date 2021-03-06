package com.chillibits.coronaaid.model.dto

import com.chillibits.coronaaid.shared.yearsBetween
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

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
        val done: Boolean,
        @JacksonXmlElementWrapper(localName = "contactData")
        @JacksonXmlProperty(localName = "contactItem")
        val contactData: Set<ContactItemDto>,
        @JacksonXmlElementWrapper(localName = "tests")
        @JacksonXmlProperty(localName = "test")
        val tests: Set<TestDto>,
        @JacksonXmlElementWrapper(localName = "initialDiseases")
        @JacksonXmlProperty(localName = "initialDisease")
        val initialDiseases: Set<DiseaseDto>,
        @JacksonXmlElementWrapper(localName = "historyItems")
        @JacksonXmlProperty(localName = "historyItem")
        val historyItems: Set<HistoryItemDto>
) {
        val age = this.birthDate.yearsBetween(LocalDate.now())
        val updateFlag = this.historyItems.any {
                LocalDate.now() == Instant.ofEpochMilli(it.timestamp).atZone(ZoneId.systemDefault()).toLocalDate()
        }
}