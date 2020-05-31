package com.chillibits.coronaaid.model.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "initial_disease")
class InitialDisease (

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int,

    //Foreign key to the affected infected
    @ManyToOne
    @JoinColumn(name = "infected_id")
    private val infectedId: Infected,

    // Foreign key to the diseases
    @ManyToOne
    @JoinColumn(name = "disease_id")
    private val diseaseId: Disease,

    // Individual degree of danger (percentage)
    private val degreeOfDanger: Int
)