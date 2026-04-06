package com.fintech.banking_core.config.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimNames;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class JwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

//    @Autowired
//    private KeycloakConfiguration keycloakConfiguration;
    @Value("${token.jwt.principal-attribute:}")
    private String principleAttribute; // = "preferred_username";
    @Value("${keycloak.resource:}")
    private String kcResource; // = "core-banking-client";

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        Collection<GrantedAuthority> authorities = Stream.concat(jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()).collect(Collectors.toSet());
        return new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrinciplealClaimName(jwt));
    }

    private String getPrinciplealClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if (principleAttribute != null) {
            claimName = principleAttribute;
        }
        return jwt.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> resourceRoles;
        if (jwt.getClaim("resource_access") != null) {
            resourceAccess = jwt.getClaim("resource_access");
            if (resourceAccess.get(kcResource) != null) {
                resource = (Map<String, Object>) resourceAccess.get(kcResource);
                if (resource != null) {
                    resourceRoles = (Collection<String>) resource.get("roles");
                    return resourceRoles.stream()
                            .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
                            .collect(Collectors.toSet());
                }
            }
        }
        return Set.of();
    }
}
