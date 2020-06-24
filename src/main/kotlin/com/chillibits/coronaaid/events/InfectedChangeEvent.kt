package com.chillibits.coronaaid.events

import com.chillibits.coronaaid.model.db.Infected
import org.springframework.context.ApplicationEvent

class InfectedChangeEvent(source: Any, val changedInfected : Set<Infected>) : ApplicationEvent(source)