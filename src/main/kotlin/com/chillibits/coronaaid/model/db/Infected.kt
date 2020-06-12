package com.chillibits.coronaaid.model.db

import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "infected")
data class Infected (

        // Fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int = 0,

        // Forename of the infected person
        val forename: String,

        // Surname of the infected person
        val surname: String,

        // Birth date of the infected person
        val birthDate: LocalDate,

        // City, where the infected person lives
        val city: String,

        // Matching postal code
        @Column(length = 10)
        val postalCode: String,

        // Street, where the infected person lives
        val street: String,

        // House, where the infected person lives
        @Column(length = 5)
        val houseNumber: String,

        // Exact gps coordinates of the house of the infected person
        val lat: Double,
        val lon: Double,

        //Health insurance number of the infected person
        val healthInsuranceNumber: String,

        // Timestamp of last locking update
        var lockedTimestamp: Long,

        // List of contact data key-value pairs
        @OneToMany(mappedBy = "infectedId", fetch = FetchType.EAGER)
        @Fetch(value = FetchMode.SUBSELECT)
        val contactData: List<ContactItem> = emptyList(),

        // List of tests
        @OneToMany(mappedBy = "infectedId")
        val tests: List<Test> = emptyList(),

        // List of initial diseases
        @OneToMany(mappedBy = "infectedId", fetch = FetchType.EAGER)
        @Fetch(value = FetchMode.SUBSELECT)
        val initialDiseases: List<InitialDisease> = emptyList(),

        // List of history items
        @OneToMany(mappedBy = "infectedId", fetch = FetchType.EAGER)
        @Fetch(value = FetchMode.SUBSELECT)
        val historyItems: List<HistoryItem> = emptyList(),

        // List of residential groups
        @ManyToMany(mappedBy = "infected", fetch = FetchType.EAGER)
        @Fetch(value = FetchMode.SUBSELECT)
        val residentialGroups: List<ResidentialGroup> = emptyList()

)