package com.chatapp.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow all endpoints
                .allowedOriginPatterns(
                    "http://localhost:*",     // Dev Vite server
                    "https://*.vercel.app"    // All Vercel deploys
                )
                .allowedMethods("*")          // GET, POST, etc.
                .allowedHeaders("*")          // Any headers
                .allowCredentials(true);      // If you use cookies or tokens
    }
}
