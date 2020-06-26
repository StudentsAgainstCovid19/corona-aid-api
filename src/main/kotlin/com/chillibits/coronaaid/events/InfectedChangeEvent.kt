package com.chillibits.coronaaid.events

import org.springframework.context.ApplicationEvent

class InfectedChangeEvent(source: Any, val changedInfected: Set<Int>) : ApplicationEvent(source)