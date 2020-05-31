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
        private val id: Int = 0,

        // Forename of the infected person
        private val forename: String,

        // Surname of the infected person
        private val surname: String,

        // Birth date of the infected person
        @Temporal(TemporalType.DATE)
        private val birthDate: Date,

        // City, where the infected person lives
        private val city: String,

        // Matching postal code
        private val postalCode: Int,

        // Street, where the infected person lives
        private val street: String,

        // House, where the infected person lives
        private val houseNumber: Int,

        // Exact gps coordinates of the house of the infected person
        private val lat: Double,
        private val lon: Double,

        // List of contact data key-value pairs
        @OneToMany(mappedBy = "infectedId")
        private val contactData: List<ContactItem> = emptyList(),

        // List of tests
        @OneToMany(mappedBy = "infectedId")
        private val tests: List<Test> = emptyList(),

        // List of initial diseases
        @OneToMany(mappedBy = "infectedId")
        private val initialDiseases: List<InitialDisease> = emptyList(),

        // List of history items
        @OneToMany(mappedBy = "infectedId")
        private val historyItems: List<HistoryItem> = emptyList(),

        // List of residential groups
        @ManyToMany(mappedBy = "infected")
        private val residentialGroups: List<ResidentialGroup> = emptyList()
)