package com.chillibits.coronaaid.model.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "disease")
class Disease {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int = 0

    // List of initial diseases
    @OneToMany
    private val initialDiseases: List<InitialDisease> = emptyList()

    // Degree of danger of the symptom in general (percentage)
    private val degreeOfDanger: Int = 0

    // Probability of occurrence in the wild (percentage)
    private val probability: Int = 0
}