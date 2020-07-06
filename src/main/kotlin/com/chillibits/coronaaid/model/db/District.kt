package com.chillibits.coronaaid.model.db

import org.locationtech.jts.geom.Polygon
import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "district")
data class District(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        @NotNull(message = "Name cannot be null")
        @NotBlank(message = "Name cannot be blank")
        val name: String,

        @NotNull(message = "City cannot be null")
        @NotBlank(message = "City cannot be blank")
        val city: String,

        @Column(length = 10)
        @Size(min = 2, max = 10, message = "Postal code not valid")
        val postalCode: String,

        @Column(columnDefinition = "polygon")
        @NotNull(message = "Geometry cannot be null")
        val geometry: Polygon
)