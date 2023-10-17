package com.sinter.cookspire.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Value("${current.profile}")
    String profile;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println(profile);
        if (profile.equalsIgnoreCase("dev")) {
            System.out.println("in here!!");
            http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/verify/user/**", "/refresh/token", "/docs/**", "/swagger-ui/**",
                            "v3/api-docs/**")
                    .permitAll()
                    .anyRequest().permitAll());
        } else {
            http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests((requests) -> requests
                    .requestMatchers("/verify/user/**", "/refresh/token", "/docs/**", "/swagger-ui/**",
                            "v3/api-docs/**")
                    .permitAll()
                    .anyRequest().authenticated()).sessionManagement(
                            (sessionManage) -> sessionManage.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        }
        return http.build();
    }

}