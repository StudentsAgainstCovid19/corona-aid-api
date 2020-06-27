package com.chillibits.coronaaid.shared

import org.springframework.context.i18n.LocaleContextHolder
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

object DateTimeConverters {

    /**
     * Date converters
     */
    val DATE_FULL_LOCALIZED: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).localizedBy(LocaleContextHolder.getLocale())

    /**
     * Time converters
     */
    val TIME_HOURS_MINUTES: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
}