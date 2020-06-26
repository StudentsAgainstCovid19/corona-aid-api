package com.chillibits.coronaaid.model.db

import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

@Entity
@Table(name = "symptom")
data class Symptom (

        // Fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        // List of history items
        @ManyToMany(mappedBy = "symptoms")
        val historyItems: Set<HistoryItem>,

        // Name or indication of the symptom
        @NotNull(message = "Symptom name cannot be null")
        @NotBlank(message = "Symptom name cannot be blank")
        val name: String,

        // Degree of danger of the symptom (1 - 10)
        @Min(value = 1, message = "Degree of danger must be > 0")
        @Max(value = 10, message = "Degree of danger must be <= 10")
        val degreeOfDanger: Int,

        // Probability of occurrence in the wild (percentage)
        @Min(value = 1, message = "Symptom probability must be > 0")
        @Max(value = 100, message = "Symptom probability must be <= 100")
        val probability: Int
) {
        override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false
                other as Symptom
                return id == other.id
        }

        override fun hashCode() = id
}