package com.elearning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

// src/main/java/com/elearning/config/SecurityConfig.java

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // **désactive complètement CSRF** pour simplifier vos appels de tests
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        // autorise **toutes** les requêtes sur /api/** (GET, POST…)
                        .requestMatchers("/api/**").permitAll()
                        // pour le reste, on peut tout autoriser (ou ajuster au besoin)
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}
