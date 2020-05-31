package com.chillibits.coronaaid.model.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "symptom")
class Symptom (

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int,

    // List of history items
    @ManyToMany
    @JoinTable(
            name = "history_symptoms",
            joinColumns = [JoinColumn(name = "history_item_id", referencedColumnName = "id")],
            inverseJoinColumns = [JoinColumn(name = "symptom_id", referencedColumnName = "id")]
    )
    private val historyItems: List<HistoryItem>,

    // Name or indication of the symptom
    private val name: String,

    // Degree of danger of the symptom in general (percentage)
    private val degreeOfDanger: Int,

    // Probability of occurrence in the wild (percentage)
    private val probability: Int
)