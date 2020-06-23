package com.chillibits.coronaaid.model.db

import javax.persistence.*

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
        val name: String,

        // Degree of danger of the symptom in general (percentage)
        val degreeOfDanger: Int,

        // Probability of occurrence in the wild (percentage)
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