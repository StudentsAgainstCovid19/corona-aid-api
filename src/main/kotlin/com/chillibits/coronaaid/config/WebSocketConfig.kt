package com.chillibits.coronaaid.config

import com.chillibits.coronaaid.allowedOrigins
import com.chillibits.coronaaid.shared.SocketTextHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(infectedHandler(), "/realtime/infected").setAllowedOrigins(*allowedOrigins.toTypedArray())
    }

    @Bean
    fun infectedHandler(): WebSocketHandler = SocketTextHandler()

    @Bean
    fun serverTaskScheduler(): TaskScheduler {
        val taskScheduler = ThreadPoolTaskScheduler()
        taskScheduler.poolSize = 3
        taskScheduler.isDaemon = true
        return taskScheduler
    }
}