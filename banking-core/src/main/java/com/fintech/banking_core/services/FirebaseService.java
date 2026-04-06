package com.fintech.banking_core.services;

import com.fintech.banking_core.customer.dto.CustomerDTO;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

public interface FirebaseService {
    UserRecord createUser(CustomerDTO customerDTO) throws FirebaseAuthException;

    UserRecord updateUser(CustomerDTO customerDTO);

    void deleteUser(String uid) throws FirebaseAuthException;

    UserRecord getUser(String uid);

    String getFirebaseToken(String keycloakId);

    String getFirebaseTokenByEmail(String email);

    void sendNotificationToUser(String token, String title, String body);

    void sendNotificationToTopic(String topic, String title, String body);

}
