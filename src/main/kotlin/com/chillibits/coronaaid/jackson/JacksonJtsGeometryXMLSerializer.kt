package com.chillibits.coronaaid.jackson

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import org.locationtech.jts.geom.Geometry
import org.locationtech.jts.geom.LineString
import org.locationtech.jts.geom.Polygon
import java.lang.UnsupportedOperationException

class JacksonJtsGeometryXMLSerializer: JsonSerializer<Geometry>() {

    override fun serialize(geometry: Geometry, generator: JsonGenerator, ctx: SerializerProvider?) {
        if(geometry is Polygon) {
            serializePolygon(geometry, generator)
        } else {
            throw UnsupportedOperationException("JtsGeometryXMLSerializer: Unsupported geometry type!")
        }
    }

    private fun serializePolygon(geometry: Polygon, generator: JsonGenerator) {
        generator.writeStartObject()
        generator.writeStringField("name", "Polygon")

        serializeLineString(geometry.exteriorRing, generator)
        for(i in 0 until geometry.numInteriorRing) {
            val ring = geometry.getInteriorRingN(i)
            serializeLineString(ring, generator)
        }

        generator.writeEndObject()
    }

    private fun serializeLineString(lineStr: LineString, generator: JsonGenerator) {
        generator.writeObjectFieldStart("ring")

        for(cord in lineStr.coordinates) {
            generator.writeObjectFieldStart("point")
            generator.writeNumberField("lon", cord.x)
            generator.writeNumberField("lat", cord.y)
            generator.writeEndObject()
        }

        generator.writeEndObject()
    }

    override fun handledType() = Geometry::class.java
}