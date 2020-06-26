package com.chillibits.coronaaid.events

import com.chillibits.coronaaid.model.dto.InfectedRealtimeDto
import org.springframework.context.ApplicationEvent

class SseDataPreparedEvent(source: Any, val changedInfected: Set<InfectedRealtimeDto>) : ApplicationEvent(source)