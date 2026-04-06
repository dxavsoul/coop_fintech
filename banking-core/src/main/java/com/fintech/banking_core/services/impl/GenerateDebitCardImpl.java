package com.fintech.banking_core.services.impl;

import com.fintech.banking_core.services.GenerateDebitCard;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GenerateDebitCardImpl implements GenerateDebitCard {
    private Random random = new Random(System.currentTimeMillis());

    /**
     * Generates a debit card number based on the provided NUBAN and length.
     * @param nuban  The NUBAN to use for generating the debit card number.
     * @param length The desired length of the debit card number.
     * @return A string representing the generated debit card number.
     */
    @Override
    public String generateDebitCardNumber(String nuban, int length) {
        // The number of random digits that we need to generate is equal to the
        // total length of the card number minus the start digits given by the
        // user, minus the check digit at the end.
        int randomNumberLength = length - (nuban.length() + 1);

        StringBuilder builder = new StringBuilder(nuban);
        for (int i = 0; i < randomNumberLength; i++) {
            int digit = this.random.nextInt(10);
            builder.append(digit);
        }

        // Do the Luhn algorithm to generate the check digit.
        int checkDigit = this.getCheckDigit(builder.toString());
        System.out.println("checkDigit " + checkDigit);
        builder.append(checkDigit);

        return builder.toString();
    }

    /**
     * Generates a random CVV number.
     * @return An integer representing the generated CVV number.
     */
    @Override
    public int generateCvvNumber() {
        return random.nextInt(999);
    }

    /**
     * Validates the provided debit card number using the Luhn algorithm.
     * @param cardNumber The debit card number to validate.
     * @return True if the card number is valid, false otherwise.
     */
    @Override
    public boolean validateDebitCard(String cardNumber) {
        // Check if the card number is null or empty
        if (cardNumber == null || cardNumber.isEmpty()) {
            return false;
        }

        // Check if the card number contains only digits
        if (!cardNumber.matches("\\d+")) {
            return false;
        }

        // Check if the card number length is valid (16 digits)
        if (cardNumber.length() != 16) {
            return false;
        }

        // Perform Luhn algorithm check
        int sum = 0;
        boolean alternate = false;
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n -= 9;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    /**
     * Generates the check digit required to make the given debit card number
     * valid (i.e. pass the Luhn check)
     *
     * @param cardNumber
     *            The debit card number for which to generate the check digit.
     * @return The check digit required to make the given debit card number
     *         valid.
     */
    private int getCheckDigit(String cardNumber) {
        // Get the sum of all the digits, however we need to replace the value
        // of the first digit, and every other digit, with the same digit
        // multiplied by 2. If this multiplication yields a number greater
        // than 9, then add the two digits together to get a single digit
        // number.
        //
        // The digits we need to replace will be those in an even position for
        // card numbers whose length is an even number, or those is an odd
        // position for card numbers whose length is an odd number. This is
        // because the Luhn algorithm reverses the card number, and doubles
        // every other number starting from the second number from the last
        // position.
//        int sum = 0;
//        boolean alternate = false;
//        for (int i = cardNumber.length() - 1; i >= 0; i--) {
//            int n = Integer.parseInt(cardNumber.substring(i, i + 1));
//            if (alternate) {
//                n *= 2;
//                if (n > 9) {
//                    n -= 9;
//                }
//            }
//            sum += n;
//            alternate = !alternate;
//        }
//        return (10 - (sum % 10)) % 10;
        int sum = 0;
        for (int i = 0; i < cardNumber.length(); i++) {

            // Get the digit at the current position.
            int digit = Integer.parseInt(cardNumber.substring(i, (i + 1)));

            if ((i % 2) == 0) {
                digit = digit * 2;
                if (digit > 9) {
                    digit = (digit / 10) + (digit % 10);
                }
            }
            sum += digit;
        }

        // The check digit is the number required to make the sum a multiple of
        // 10.
        int mod = sum % 10;
        return ((mod == 0) ? 0 : 10 - mod);
    }
}
