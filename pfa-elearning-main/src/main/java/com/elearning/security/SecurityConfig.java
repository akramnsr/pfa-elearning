package com.elearning.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // on autorise l’accès public aux pages Thymeleaf de listing
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/admin/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/quizzes/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/formations/**").permitAll()
                        // on autorise les endpoints REST en GET sans auth
                        .requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                        // on laisse ouvert l’endpoint d’authentification (à créer)
                        .requestMatchers("/api/auth/**").permitAll()
                        // tout le reste nécessite l’authentification
                        .anyRequest().authenticated()
                )
                // par exemple du HTTP Basic (ou remplacez par JWTFilter si vous préférez)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
