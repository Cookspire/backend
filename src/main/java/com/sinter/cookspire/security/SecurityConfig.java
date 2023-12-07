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
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

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

                if (profile.equalsIgnoreCase("dev")) {
                        http.csrf((csrf) -> csrf.disable())
                                        .headers(headers -> headers.contentSecurityPolicy(
                                                        cps -> cps.policyDirectives("script-src 'self' ....."))
                                                        .xssProtection(
                                                                        xss -> xss.headerValue(
                                                                                        XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)))
                                        .authorizeHttpRequests((requests) -> requests
                                                        .requestMatchers("*")
                                                        .permitAll()
                                                        .anyRequest().permitAll());
                } else {
                        http.csrf((csrf) -> csrf.disable()).headers(headers -> headers.contentSecurityPolicy(
                                        cps -> cps.policyDirectives("script-src 'self' ....."))
                                        .xssProtection(
                                                        xss -> xss.headerValue(
                                                                        XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)))
                                        .authorizeHttpRequests((requests) -> requests
                                                        .requestMatchers("/verify/user/**", "/persist/user",
                                                                        "/fetchAll/trending/post/**", "/refresh/token",
                                                                        "/docs/**",
                                                                        "/swagger-ui/**", "/actuators/**",
                                                                        "/actuators/prometheus/**",
                                                                        "v3/api-docs/**", "/fetch/trending/profile**",
                                                                        "/fetchAll/trending/post/**",
                                                                        "/search/cookspire/**",
                                                                        "/search/recipe/**", "/filter/recipe/**",
                                                                        "/search/people/**",
                                                                        "/search/recipe**", "/fetch/recipe/cuisine/**",
                                                                        "/filter/recipe", "/fetch/complete/recipe/**",
                                                                        "/search/cookspire/**")
                                                        .permitAll()
                                                        .anyRequest().authenticated())
                                        .sessionManagement(
                                                        (sessionManage) -> sessionManage.sessionCreationPolicy(
                                                                        SessionCreationPolicy.STATELESS))
                                        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                }
                return http.build();
        }

}
