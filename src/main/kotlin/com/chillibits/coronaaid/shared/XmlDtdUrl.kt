package com.chillibits.coronaaid.shared

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class XmlDtdUrl(
        val url: String,
        val rootElement: String
)