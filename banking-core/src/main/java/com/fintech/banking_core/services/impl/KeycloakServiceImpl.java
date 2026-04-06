package com.fintech.banking_core.services.impl;

import com.fintech.banking_core.services.KeycloakService;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KeycloakServiceImpl implements KeycloakService {
    @Value("${keycloak.realm}")
    private String realm;
    private final Keycloak keycloak;

//    @Override
//    public void assignRole(String userId, String roleName) {
//    }
//
//    @Override
//    public void unAssignRole(String userId, String roleName) {
//
//    }

    @Override
    public boolean deleteUser(String userId) {
        try {
            UsersResource user = getUsersResource();
            Response response = user.delete(userId);
            if (response.getStatus() == 204) {
                log.info("User with ID {} deleted successfully", userId);
                return true;
            } else {
                log.error("Failed to delete user with ID {}: {}", userId, response.getStatusInfo());
                return false;
            }
        } catch (RuntimeException e) {
            log.error("Error deleting user: {}", e.getMessage());
            throw new RuntimeException("Error deleting user: " + e.getMessage());
        }
    }

    @Override
    public void forgotPassword(String username) {
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> usersRepresentation = usersResource.searchByUsername(username, true);
        UserRepresentation userRepresentation = usersRepresentation.getFirst();

        UserResource userResource = usersResource.get(userRepresentation.getId());
        userResource.executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }

    @Override
    public void sendVerificationEmail(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    @Override
    public Response createUser(String username, String email, String password, String firstName, String lastName) {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(firstName);
        userRepresentation.setLastName(lastName);
        userRepresentation.setUsername(username);
        userRepresentation.setEmail(email);
        userRepresentation.setEmailVerified(false);

        // Set password
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(false); // Set to false to make the password permanent

        userRepresentation.setCredentials(List.of(credentialRepresentation));

        UsersResource usersResource = getUsersResource();

        Response response = usersResource.create(userRepresentation);

        if (response.getStatus() != 201) {
            throw new RuntimeException("Failed to create user: " + response.getStatus());
        }
        log.info("New user has been created");
        return response;
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

//    private RolesResource getRolesResource() {
//        return keycloak.realm(realm).roles();
//    }
}
