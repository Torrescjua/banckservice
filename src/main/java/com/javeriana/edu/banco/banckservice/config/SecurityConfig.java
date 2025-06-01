package com.javeriana.edu.banco.banckservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.config.http.SessionCreationPolicy;

import com.javeriana.edu.banco.banckservice.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String RETAIL_APP_IP = "10.43.103.46";
    private static final String CONCILIACION_APP_IP = "10.43.96.39";

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .cors(cors -> {})
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/api/retail/compras").access(new WebExpressionAuthorizationManager(
                    "hasIpAddress('" + RETAIL_APP_IP + "')"
                    + " or hasIpAddress('127.0.0.1')"
                    + " or hasIpAddress('::1')"
                ))
                .requestMatchers("/api/conciliacion/**").access(new WebExpressionAuthorizationManager(
                    "hasIpAddress('" + CONCILIACION_APP_IP + "')"
                + " or hasIpAddress('127.0.0.1')"
                + " or hasIpAddress('::1')"
                ))
                .anyRequest().authenticated()
            )
            .build();
    }

    @Bean
    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
        var bean = new FilterRegistrationBean<>(new ForwardedHeaderFilter());
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
