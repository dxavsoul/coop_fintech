package com.fintech.banking_core.config;

import lombok.RequiredArgsConstructor;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class KeycloakProvider {

    @Autowired
    private KeycloakConfiguration keycloakConfiguration;

    //private static final Keycloak keycloak = null;

    @Bean
    public Keycloak getInstance() {
        return KeycloakBuilder.builder()
                .clientSecret(keycloakConfiguration.getClientSecret())
                .clientId(keycloakConfiguration.getClientId())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .realm(keycloakConfiguration.getRealm())
                .serverUrl(keycloakConfiguration.getServerURL())
                .build();
    }
}
