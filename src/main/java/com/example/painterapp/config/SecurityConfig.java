package com.example.painterapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // 🔐 Password Encryption
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 🔓 Security Rules
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            // disable csrf for mobile app
            .csrf(csrf -> csrf.disable())

            // authorize APIs
            .authorizeHttpRequests(auth -> auth

                // ✅ painter public APIs
                .requestMatchers("/api/painter/**","/api/reward/**").permitAll()
                
                
          
                // ✅ admin register & login (IMPORTANT)
                .requestMatchers(
                        "/api/admin/register",
                        "/api/admin/login",
                        "/api/admin/users/**",
                        "/api/admin/qr/**"
                ).permitAll()

                // 🔒 all other APIs secured
                .anyRequest().authenticated()
            );

        return http.build();
    }
}
