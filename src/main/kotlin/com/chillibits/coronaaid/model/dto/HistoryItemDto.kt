package com.chillibits.coronaaid.model.dto

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty

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
)