package org.springboot.taskmanager.security;


import org.springboot.taskmanager.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import jakarta.servlet.Filter;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private final TokenService tokenService;

    public SecurityConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,"api/user").permitAll()
                        .requestMatchers(HttpMethod.GET,"api/user/getById").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(httpBasic -> {
                   //http.addFilterBefore(new UserAuthenticationFilter(tokenService), AnonymousAuthenticationFilter.class);
                });
        http.addFilterAt(new UserAuthenticationFilter(tokenService), AnonymousAuthenticationFilter.class);

        //http.addFilterAt(new SimpleLoggingFilter(), AnonymousAuthenticationFilter.class);

//        List<Filter> filters = filterChainProxy.getFilters("/");
//        System.out.println("Filters: " + filters.stream()
//                .filter(f -> f != null)  // Add this to handle null filters
//                .map(f -> f.getClass().getName())
//                .collect(Collectors.toList()));

        return http.build();
    }
}
