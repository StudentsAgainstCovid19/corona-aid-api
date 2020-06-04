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
        val initialDiseases: List<InitialDisease>,

        // Name or indication of the disease
        @Column(unique = true)
        val name: String,

        // Degree of danger of the symptom in general (percentage)
        val degreeOfDanger: Int,

        // Probability of occurrence in the wild (percentage)
        val probability: Int
)