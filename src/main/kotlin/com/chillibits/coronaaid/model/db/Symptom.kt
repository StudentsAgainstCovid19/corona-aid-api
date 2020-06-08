package com.chillibits.coronaaid.model.db

import javax.persistence.*

@Entity
@Table(name = "symptom")
data class Symptom (

        // Fields
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int,

        // List of history items
        @ManyToMany(mappedBy = "symptoms")
        val historyItems: List<HistoryItem>,

        // Name or indication of the symptom
        val name: String,

        // Degree of danger of the symptom in general (percentage)
        val degreeOfDanger: Int,

        // Probability of occurrence in the wild (percentage)
        val probability: Int
)