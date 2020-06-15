package com.chillibits.coronaaid.model.db

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "disease")
data class Disease (

        // Fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        // List of initial diseases
        @OneToMany
        val initialDiseases: Set<InitialDisease>,

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

                if (id != other.id) return false

                return true
        }

        override fun hashCode(): Int {
                return id
        }
}