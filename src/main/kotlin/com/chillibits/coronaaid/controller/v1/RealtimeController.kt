package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.shared.SseEmitterStorage
import io.swagger.annotations.Api
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.lang.Exception
import java.time.LocalTime

@RestController
@Api(value = "Realtime Endpoint", tags = ["realtime"])
class RealtimeController {

    @GetMapping(path = ["/realtime/sse"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun sseConnection(): SseEmitter {
        val emitter = SseEmitter()
        SseEmitterStorage.addEmitter(emitter)
        return emitter
    }

}