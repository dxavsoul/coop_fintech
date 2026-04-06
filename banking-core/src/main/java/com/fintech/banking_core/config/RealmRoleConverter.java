//package com.fintech.banking_core.config;
//
//import org.springframework.core.convert.converter.Converter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.stereotype.Component;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Component
//public class RealmRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
//    @Override
//    public Collection<GrantedAuthority> convert(Jwt source) {
//        final Map<String, List<String>> realmAccess = (Map<String, List<String>>) source.getClaims().get("realm_access");
//        return realmAccess.get("roles")
//                .stream()
//                .map(roleName -> "ROLE_" + roleName) // prefix required by Spring Security for roles.
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toSet());
//    }
//}
