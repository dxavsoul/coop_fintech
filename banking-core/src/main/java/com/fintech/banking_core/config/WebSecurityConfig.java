package com.fintech.banking_core.config;

import jakarta.ws.rs.HttpMethod;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@Configuration
public class WebSecurityConfig {

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web.ignoring().requestMatchers(
                    HttpMethod.POST,
                    "/public/**",
                    "/api/v1/customer/register"
            );
            web.ignoring().requestMatchers(
                    HttpMethod.GET,
                    "/public/**"
            );
            web.ignoring().requestMatchers(
                    HttpMethod.DELETE,
                    "/public/**"
            );
            web.ignoring().requestMatchers(
                    HttpMethod.PUT,
                    "/public/**"
            );
            web.ignoring().requestMatchers(
                    HttpMethod.OPTIONS,
                    "/**"
            ).requestMatchers(
                    "/configuration/**", "/swagger*/**", "/v3/api-docs/**",
                    "/webjars/**", "/swagger-ui/**", "/v3/api-docs/swagger-config", "/v3/api-docs/swagger-config/**",
                    "/swarger-resources/**", "/swagger-ui/index.html", "/swagger-ui.html", "/v3/api-docs",
                    "/api-docs/**", "/api-docs/swagger-config", "/api-docs/swagger-config/**"
            );
        };
    }
}
