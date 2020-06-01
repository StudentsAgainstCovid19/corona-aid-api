package com.chillibits.coronaaid.model.db

import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.persistence.Temporal
import javax.persistence.TemporalType

@Entity
@Table(name = "infected")
class Infected (

        // Fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        // Forename of the infected person
        val forename: String,

        // Surname of the infected person
        val surname: String,

        // Birth date of the infected person
        @Temporal(TemporalType.DATE)
        val birthDate: Date,

        // City, where the infected person lives
        val city: String,

        // Matching postal code
        val postalCode: Int,

        // Street, where the infected person lives
        val street: String,

        // House, where the infected person lives
        val houseNumber: Int,

        // Exact gps coordinates of the house of the infected person
        val lat: Double,
        val lon: Double,

        // List of contact data key-value pairs
        @OneToMany(mappedBy = "infectedId")
        val contactData: List<ContactItem> = emptyList(),

        // List of tests
        @OneToMany(mappedBy = "infectedId")
        val tests: List<Test> = emptyList(),

        // List of initial diseases
        @OneToMany(mappedBy = "infectedId")
        val initialDiseases: List<InitialDisease> = emptyList(),

        // List of history items
        @OneToMany(mappedBy = "infectedId")
        val historyItems: List<HistoryItem> = emptyList(),

        // List of residential groups
        @ManyToMany(mappedBy = "infected")
        val residentialGroups: List<ResidentialGroup> = emptyList()
)