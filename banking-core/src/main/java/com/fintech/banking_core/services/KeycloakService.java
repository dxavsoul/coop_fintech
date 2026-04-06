package com.fintech.banking_core.services;

import jakarta.ws.rs.core.Response;

public interface KeycloakService {

//    void assignRole(String userId, String roleName);
//
//    void unAssignRole(String userId, String roleName);

    boolean deleteUser(String userId);

    void forgotPassword(String username);

    void sendVerificationEmail(String userId);

    Response createUser(String username, String email, String password, String firstName, String lastName);
}
