package com.chillibits.coronaaid.controller.v1

import com.chillibits.coronaaid.events.InfectedChangeEvent
import com.chillibits.coronaaid.model.dto.InfectedRealtimeDto
import com.chillibits.coronaaid.repository.ConfigRepository
import com.chillibits.coronaaid.shared.ConfigKeys
import com.chillibits.coronaaid.shared.SseEmitterStorage
import com.chillibits.coronaaid.shared.toCompressed
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import io.swagger.annotations.Api
import org.apache.commons.collections4.queue.CircularFifoQueue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.event.EventListener
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter.SseEventBuilder
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

@RestController
@Api(value = "Realtime Endpoint", tags = ["realtime"])
class RealtimeController {

    @Autowired
    private lateinit var configRepository: ConfigRepository

    private var sseEventId = 0

    private val circularBuffer = CircularFifoQueue<SseEventBuilder>(5)
    private val circularBufferLock = ReentrantLock()

    @GetMapping(path = ["/realtime/sse"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun sseConnection(@RequestHeader(name = "Last-Event-ID", required = false, defaultValue = "-1") lastIdStr: String): SseEmitter {
        val emitter = SseEmitter()
        SseEmitterStorage.addEmitter(emitter) //TODO: replace with CopyOnWriteList

        val lastId = lastIdStr.toInt()
        if(lastId != -1 && lastId != sseEventId && sseEventId > lastId) {
            if(sseEventId-lastId <= 5) {
                circularBufferLock.withLock {
                    circularBuffer.stream().skip((circularBuffer.size-(sseEventId-lastId)).toLong()).forEach { emitter.send(it) }
                }
            } else {
                circularBufferLock.withLock {
                    circularBuffer.forEach { emitter.send(it) }
                }
            }
        }
        return emitter
    }

    @EventListener
    fun handleInfectedChangedEvent(event: InfectedChangeEvent) {
        sseEventId++

        val offset = (configRepository.findByConfigKey(ConfigKeys.CK_AUTO_RESET_OFFSET)?.configValue?.toLong() ?: 0) * 1000
        val mapped = event.changedInfected.map { it.toCompressed(offset) }.map {
            InfectedRealtimeDto(
                    it.id,
                    it.done,
                    it.lastUnsuccessfulCallToday != null,
                    it.lastUnsuccessfulCallTodayString,
                    it.locked
            )
        }

        val content = XML_MAPPER.writeValueAsString(mapped)
        val emittedData = SseEmitter.event()
                .data(content)
                .id(sseEventId.toString())

        circularBufferLock.withLock {
            circularBuffer.add(emittedData)
        }

        SseEmitterStorage.sendToAll(emittedData)
    }

    companion object {
        @JvmField val XML_MAPPER = XmlMapper()
    }

}