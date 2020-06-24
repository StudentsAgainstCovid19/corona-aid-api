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
            println("COMLETE")
            mutex.withLock { emitters.remove(emitter) }
        }
        emitter.onError {
            println("ERROR")
            mutex.withLock { emitters.remove(emitter) }
        }
        emitter.onTimeout {
            println("TIMEOUT")
            mutex.withLock { emitters.remove(emitter) }
        }

        mutex.withLock {
            emitters.add(emitter)
        }
    }

    fun sendToAll(obj: Any) {
        mutex.withLock {
            val failed = mutableListOf<SseEmitter>()

            for (it in emitters) {
                try {
                    it.send(obj)
                } catch (ex: Exception) {
                    failed.add(it)
                }
            }

            emitters.removeAll(failed)
        }
    }

}