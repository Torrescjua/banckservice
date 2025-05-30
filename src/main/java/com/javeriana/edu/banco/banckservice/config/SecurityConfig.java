package com.javeriana.edu.banco.banckservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;

import com.javeriana.edu.banco.banckservice.jwt.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String RETAIL_APP_IP = "10.43.102.241";

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .csrf(csrf -> csrf.disable())
          .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
          .authenticationProvider(authProvider)
          .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
          .authorizeHttpRequests(auth -> auth
              // 1) endpoints públicos de autenticación
              .requestMatchers("/auth/**").permitAll()

              // 2) solo desde la IP autorizada podrá llamar a la ruta de Retail
              .requestMatchers("/api/retail/compras")
                .access(new WebExpressionAuthorizationManager(
                    "hasIpAddress('" + RETAIL_APP_IP + "')"
                    + "or hasIpAddress('127.0.0.1') "
                    + "or hasIpAddress('::1')"
                ))

              // 3) el resto requieren autenticación JWT
              .anyRequest().authenticated()
          );

        return http.build();
    }

    @Bean
    public FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
        var bean = new FilterRegistrationBean<>(new ForwardedHeaderFilter());
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
