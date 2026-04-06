package com.fintech.banking_core.services;

import org.springframework.stereotype.Component;

@Component
public interface GenerateDebitCard {

    /**
     * Generates a debit card number based on the provided NUBAN (Nigeria Uniform Bank Account Number).
     *
     * @param nuban  The NUBAN to use for generating the debit card number.
     * @param length The desired length of the debit card number.
     * @return A string representing the generated debit card number.
     */
    String generateDebitCardNumber(String nuban, int length);

    /**
     * Generates a CVV (Card Verification Value) number for the debit card.
     *
     * @return An integer representing the generated CVV number.
     */
    int generateCvvNumber();

    /**
     * Validates the provided debit card number.
     *
     * @param cardNumber The debit card number to validate.
     * @return True if the card number is valid, false otherwise.
     */
    boolean validateDebitCard(String cardNumber);
}
