package com.example.zonadegolbackend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;





    @Bean
    public SecurityFilterChain secutityFilterChain(HttpSecurity http, AuthenticationManagerBuilder authenticationManagerBuilder, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/**").permitAll();
                    auth.requestMatchers("/api/usuario/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/api/temporadas/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/api/noticias/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/api/liga/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/api/estadisticas/admin/**").hasRole("ADMIN");
                    auth.requestMatchers("/api/equipo/crear").hasRole("ENTRENADOR");
                    auth.requestMatchers("/api/equipo/editar/**").hasRole("ENTRENADOR");
                    auth.requestMatchers("/api/jornada/arbitro/**").hasRole("ARBITRO");
                    auth.requestMatchers("/api/jornada/crear").hasRole("ADMIN");
                    auth.requestMatchers("/api/jornada/editar/**").hasRole("ADMIN");
                    auth.requestMatchers("/api/jornada/eliminar/**").hasRole("ADMIN");
                    auth.requestMatchers("/api/jornada/generar/**").hasRole("ADMIN");
                    auth.anyRequest().permitAll();
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

