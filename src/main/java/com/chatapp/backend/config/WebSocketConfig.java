package com.chatapp.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // Configure message broker (topics to subscribe + app prefixes)
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // Clients subscribe to this
        config.setApplicationDestinationPrefixes("/app"); // Messages sent to this
    }

    // Register STOMP endpoint with SockJS fallback
    @Override
public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry
        .addEndpoint("/chat")
        .setAllowedOriginPatterns(
            "http://localhost:*",
            "https://*.vercel.app"
        )
        .withSockJS();
}

}
