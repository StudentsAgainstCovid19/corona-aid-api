package com.chillibits.coronaaid.config

import com.chillibits.coronaaid.shared.SocketTextHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

@Configuration
@EnableWebSocket
class WebSocketConfig : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(infectedHandler(), "/realtime/infected").setAllowedOrigins("http://localhost:63342", "http://localhost:63343", "https://sac19.jatsqi.com", "https://dev.sac19.jatsqi.com", "https://corona-aid-ka.de", "https://dev.corona-aid-ka.de")
    }

    @Bean
    fun infectedHandler(): WebSocketHandler = SocketTextHandler()
}