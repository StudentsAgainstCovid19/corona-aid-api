package com.chillibits.coronaaid.config

import com.chillibits.coronaaid.jackson.XmlDtdInjector
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class SpringWebMvcConfig: WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(XmlDtdInjector())
        super.addInterceptors(registry)
    }
}