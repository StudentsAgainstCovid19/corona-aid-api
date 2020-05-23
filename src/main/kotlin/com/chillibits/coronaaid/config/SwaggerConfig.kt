package com.chillibits.coronaaid.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.context.ServletContextAware
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.paths.RelativePathProvider
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2
import javax.servlet.ServletContext

@Configuration
@EnableSwagger2
class SwaggerConfig: ServletContextAware {

    // Variables as objects
    private lateinit var context: ServletContext

    @Bean
    public fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_12)
                .host("api.corona-aid.chillibits.com")
                .pathProvider(object: RelativePathProvider(context) {

                })
                .enableUrlTemplating(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.chillibits.coronaaid"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
    }

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

    override fun setServletContext(servletContext: ServletContext) {
        context = servletContext
    }
}