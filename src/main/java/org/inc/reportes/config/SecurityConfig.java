package org.inc.reportes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1. Desactivar CSRF (Crucial para APIs REST que no usan cookies)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. Definir que NO guardaremos estado (Stateless) - Ahorra memoria en el servidor
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // 3. Reglas de acceso (Lista Blanca)
                .authorizeHttpRequests(auth -> auth
                        // Permitir acceso público al Health Check (para AWS Load Balancer)
                        .requestMatchers("/actuator/**").permitAll()
                        // Todo lo demás requiere autenticación
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().denyAll()
                )

                // 4. Usar Basic Auth para este ejemplo (En producción usarías .oauth2ResourceServer)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // Usuario "falso" en memoria para probar la seguridad sin conectar una BD de usuarios aún
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.withDefaultPasswordEncoder() // Solo para demos/dev
                .username("admin")
                .password("password123")
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(admin);
    }
}
