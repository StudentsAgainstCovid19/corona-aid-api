package com.chillibits.coronaaid.shared

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.lang.Exception
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.withLock

object SseEmitterStorage {

    private val emitters: MutableList<SseEmitter> = mutableListOf()
    private val mutex = ReentrantLock()

    fun addEmitter(emitter: SseEmitter) {
        emitter.onCompletion {
            mutex.withLock { emitters.remove(emitter) }
        }
        emitter.onError {
            mutex.withLock { emitters.remove(emitter) }
        }
        emitter.onTimeout {
            mutex.withLock { emitters.remove(emitter) }
        }

        mutex.withLock {
            emitters.add(emitter)
        }
    }

    fun sendToAll(obj: SseEmitter.SseEventBuilder) {
        mutex.withLock {
            val failed = mutableListOf<SseEmitter>()

            for (it in emitters) {
                try {
                    it.send(obj)
                    println("SEND TO CLIENT")
                } catch (ex: Exception) {
                    failed.add(it)
                }
            }

            emitters.removeAll(failed)
        }
    }

}