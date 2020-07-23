package com.chillibits.coronaaid.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.UiConfigurationBuilder
import javax.servlet.ServletContext

@Configuration
class SwaggerConfig {

    // Variables as objects
    private lateinit var context: ServletContext

    @Bean
    fun api() = Docket(DocumentationType.OAS_30)
            .host("api.corona-aid-ka.de")
            .enableUrlTemplating(true)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.chillibits.coronaaid"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo())

    @Bean
    fun uiConfig() = UiConfigurationBuilder.builder()
            .defaultModelsExpandDepth(-1)
            .build()

    private fun apiInfo() = ApiInfo(
            "Corona Aid API",
            "Student project for fighting against Covid19",
            "1.0.0",
            "https://chillibits.com/pmapp?p=privacy",
            Contact("ChilliBits", "https://www.chillibits.com", "contact@chillibits.com"),
            "ODC DbCL v1.0 License",
            "https://opendatacommons.org/licenses/dbcl/1.0/",
            emptyList()
    )
}