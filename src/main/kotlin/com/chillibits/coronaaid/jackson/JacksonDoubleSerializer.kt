package com.chillibits.coronaaid.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import java.text.DecimalFormat

class JacksonDoubleSerializer: JsonSerializer<Double>() {

    override fun serialize(value: Double, generator: JsonGenerator, ctx: SerializerProvider) {
        generator.writeNumber(FORMAT_DOUBLE_NO_SCIENTIFIC.format(value))
    }

    override fun handledType() = Double::class.java

    companion object {
        @JvmField val FORMAT_DOUBLE_NO_SCIENTIFIC = DecimalFormat("#.#").apply { maximumFractionDigits = 5 }
    }
}