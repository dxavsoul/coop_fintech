package com.fintech.banking_core.debitcard.service;

import com.fintech.banking_core.debitcard.dto.DebitCardRequest;

public interface DebitCardService {
    // Define the methods that will be implemented in the service class
    boolean requestDebitCard(DebitCardRequest debitCardRequest);

    void blockDebitCard(String cardId);

    void unblockDebitCard(String cardId);

    void updateDebitCardDetails(String cardId, String newDetails);

    void getDebitCardDetails(String cardId);

    void deleteDebitCard(String cardId);
}
