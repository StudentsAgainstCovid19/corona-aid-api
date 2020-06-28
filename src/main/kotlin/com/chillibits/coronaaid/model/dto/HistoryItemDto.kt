package com.chillibits.coronaaid.model.dto

import com.chillibits.coronaaid.shared.DateTimeConverters
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

data class HistoryItemDto (
        val id: Int,
        val infectedId : Int?,
        val timestamp: Long,
        @JacksonXmlElementWrapper(localName = "symptoms")
        @JacksonXmlProperty(localName = "symptom")
        val symptoms: Set<SymptomDto>,
        val status: Int,
        val personalFeeling: Int,
        val notes: String?
) {
        val date: String =
                ZonedDateTime
                        .ofInstant(Instant.ofEpochMilli(timestamp), ZoneId.systemDefault())
                        .format(DateTimeConverters.DATE_FULL_LOCALIZED)
}