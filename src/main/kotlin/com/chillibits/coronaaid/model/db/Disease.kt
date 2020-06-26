package com.chillibits.coronaaid.model.db

import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "disease")
data class Disease (

        // Fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        // List of initial diseases
        @ManyToMany(mappedBy = "initialDiseases")
        val initialDiseases: Set<Infected>,

        // Name or indication of the disease
        @Column(unique = true)
        @NotNull(message = "Disease name cannot be null")
        @NotBlank(message = "Disease name cannot be blank")
        val name: String,

        // Degree of danger of the symptom in general (1 - 10)
        @Min(value = 1, message = "Degree of danger must be > 0")
        @Max(value = 10, message = "Degree of danger must be <= 10")
        val degreeOfDanger: Int,

        // Probability of occurrence in the wild (percentage)
        @Min(value = 1, message = "Disease probability must be > 0")
        @Max(value = 100, message = "Disease probability must be <= 100")
        val probability: Int
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false
                other as Disease
                return id == other.id
        }

        override fun hashCode() = id
}