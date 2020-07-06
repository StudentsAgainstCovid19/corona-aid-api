package com.chillibits.coronaaid.config

import com.chillibits.coronaaid.jackson.JtsGeometryXMLSerializer
import com.fasterxml.jackson.databind.module.SimpleModule
import org.locationtech.spatial4j.io.jackson.ShapeAsGeoJSONSerializer
import org.locationtech.spatial4j.io.jackson.ShapesAsGeoJSONModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter
import javax.annotation.PostConstruct

@Configuration
class JacksonConfig {

    @Autowired
    private lateinit var xmlMapper: MappingJackson2XmlHttpMessageConverter

    @Autowired
    private lateinit var jsonMapper: MappingJackson2HttpMessageConverter

    @PostConstruct
    fun configureMappers() {
        // Configure JSON Mapper
        jsonMapper.objectMapper.registerModule(ShapesAsGeoJSONModule())

        // Configure custom XML Mapper
        val module = SimpleModule()
        module.addSerializer(JtsGeometryXMLSerializer())
        xmlMapper.objectMapper.registerModule(module)
    }

}