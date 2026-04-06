package com.fintech.banking_core.services.impl;

import com.fintech.banking_core.customer.dto.CustomerDTO;
import com.fintech.banking_core.services.FirebaseService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FirebaseServiceImpl implements FirebaseService {

    @Override
    public UserRecord createUser(CustomerDTO customerDTO) throws FirebaseAuthException {
        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(customerDTO.getEmail())
                .setEmailVerified(false)
                .setPassword(customerDTO.getPassword())
                .setDisplayName(customerDTO.getFirstName() + " " + customerDTO.getLastName())
                .setDisabled(false);

        return FirebaseAuth.getInstance().createUser(request);
    }

    @Override
    public UserRecord updateUser(CustomerDTO customerDTO) {
        return null;
    }

    @Override
    public void deleteUser(String uid) throws FirebaseAuthException {
        FirebaseAuth.getInstance().deleteUser(uid);
    }

    @Override
    public UserRecord getUser(String uid) {
        return null;
    }

    @Override
    public String getFirebaseToken(String keycloakId) {
        return "";
    }

    @Override
    public String getFirebaseTokenByEmail(String email) {
        return "";
    }

    @Override
    public void sendNotificationToUser(String token, String title, String body) {

    }

    @Override
    public void sendNotificationToTopic(String topic, String title, String body) {

    }
}
