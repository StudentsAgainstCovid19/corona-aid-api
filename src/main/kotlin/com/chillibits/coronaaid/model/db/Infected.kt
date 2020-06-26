package com.chillibits.coronaaid.model.db

import com.chillibits.coronaaid.model.mapper.truncateToMidnight
import java.time.Instant
import java.time.LocalDate
import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.PastOrPresent
import javax.validation.constraints.Size

@NamedEntityGraph(
        name = "Infected.loadAll",
        attributeNodes = [
                NamedAttributeNode("contactData"),
                NamedAttributeNode("historyItems"),
                NamedAttributeNode("initialDiseases"),
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
        @NotNull(message = "Forename cannot be null")
        @NotBlank(message = "Forename cannot be blank")
        val forename: String,

        // Surname of the infected person
        @NotNull(message = "Surname cannot be null")
        @NotBlank(message = "Surname cannot be blank")
        val surname: String,

        // Birth date of the infected person
        @PastOrPresent(message = "Timestamp in the future not allowed")
        val birthDate: LocalDate,

        // City, where the infected person lives
        @NotNull(message = "City cannot be null")
        @NotBlank(message = "City cannot be blank")
        val city: String,

        // Matching postal code
        @Column(length = 10)
        @Size(min = 2, max = 10, message = "Postal code not valid")
        val postalCode: String,

        // Street, where the infected person lives
        @NotNull(message = "Street cannot be null")
        @NotBlank(message = "Street cannot be blank")
        val street: String,

        // House, where the infected person lives
        @Column(length = 5)
        @NotNull(message = "House number cannot be null")
        @NotBlank(message = "House number cannot be blank")
        @Size(min = 1, max = 5, message = "House number must be <= 5 chars")
        val houseNumber: String,

        // Exact gps coordinates of the house of the infected person
        @Min(value = -90, message = "Latitude too small")
        @Max(value = 90, message = "Latitude too big")
        val lat: Double,
        @Min(value = -180, message = "Longitude too small")
        @Max(value = 180, message = "Longitude too big")
        val lon: Double,

        //Health insurance number of the infected person
        @NotNull(message = "Insurance number cannot be null")
        @NotBlank(message = "Insurance number cannot be blank")
        val healthInsuranceNumber: String,

        // Timestamp of last locking update
        @PastOrPresent(message = "No timestamps in the future allowed")
        var lockedTimestamp: Long,

        // List of contact data key-value pairs
        @OneToMany(mappedBy = "infectedId")
        val contactData: Set<ContactItem> = emptySet(),

        // List of tests
        @OneToMany(mappedBy = "infectedId")
        val tests: Set<Test> = emptySet(),

        // List of initial diseases
        @ManyToMany
        @JoinTable(name = "infected_initial_diseases",
                joinColumns = [JoinColumn(name = "infected_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "disease_id", referencedColumnName = "id")])
        val initialDiseases: Set<Disease> = emptySet(),

        // List of history items
        @OneToMany(mappedBy = "infectedId")
        val historyItems: Set<HistoryItem> = emptySet()
) {

    @Transient
    var done = false
        get() = this.historyItems.any { it.timestamp >= Instant.now().truncateToMidnight() && it.status == HistoryItem.STATUS_REACHED }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as Infected
        return id == other.id
    }

    override fun hashCode() = id
}