package com.chillibits.coronaaid.model.db

import java.time.LocalDate
import javax.persistence.*

@NamedEntityGraph(
        name = "Infected.loadAll",
        attributeNodes = [
                NamedAttributeNode("contactData"),
                NamedAttributeNode("historyItems"),
                NamedAttributeNode("initialDiseases"),
                NamedAttributeNode("residentialGroups"),
                NamedAttributeNode("tests")
        ]
)
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
        @OneToMany(mappedBy = "infectedId")
        val contactData: Set<ContactItem> = emptySet(),

        // List of tests
        @OneToMany(mappedBy = "infectedId")
        val tests: Set<Test> = emptySet(),

        // List of initial diseases
        @OneToMany(mappedBy = "infectedId")
        val initialDiseases: Set<InitialDisease> = emptySet(),

        // List of history items
        @OneToMany(mappedBy = "infectedId")
        val historyItems: Set<HistoryItem> = emptySet(),

        // List of residential groups
        @ManyToMany(mappedBy = "infected")
        val residentialGroups: Set<ResidentialGroup> = emptySet()

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as HistoryItem
        return id == other.id
    }

    override fun hashCode() = id
}