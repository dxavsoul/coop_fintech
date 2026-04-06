package com.fintech.banking_core.debitcard.service.Impl;

import com.fintech.banking_core.account.repository.AccountRepository;
import com.fintech.banking_core.common.enums.CardStatus;
import com.fintech.banking_core.debitcard.domain.DebitCard;
import com.fintech.banking_core.debitcard.dto.DebitCardRequest;
import com.fintech.banking_core.debitcard.repository.DebitCardRepository;
import com.fintech.banking_core.debitcard.service.DebitCardService;
import com.fintech.banking_core.services.GenerateDebitCard;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DebitCardServiceImpl implements DebitCardService {
    private final DebitCardRepository debitCardRepository;
    private final AccountRepository accountRepository;
    private final GenerateDebitCard generateDebitCard;

    private static final BigDecimal CARD_CHARGES = BigDecimal.valueOf(1100);


    @Override
    public boolean requestDebitCard(DebitCardRequest debitCardRequest) {
        log.trace("requestDebitCard({}, {}, {})", debitCardRequest.getAccountNumber(), debitCardRequest.getCardType());
        var id = debitCardRequest.getAccountNumber();

        var account = accountRepository.findById(debitCardRequest.getAccountNumber()).orElseThrow(() -> new RuntimeException("Account not found"));

        if (account.getAccountBalance().compareTo(CARD_CHARGES) <= 0) {
            log.error("Insufficient funds to request card for customer with id {}", id);
            throw new RuntimeException("Insufficient Funds!!");
        }

        account.setAccountBalance(account.getAccountBalance().subtract(CARD_CHARGES));
        DebitCard debitCardEntity = new DebitCard();
        var cardNumber = generateDebitCard.generateDebitCardNumber(account.getNubanNo(), 16);
        var cvv = generateDebitCard.generateCvvNumber();

        debitCardEntity.setStatus(CardStatus.ACTIVE);
        debitCardEntity.setCardType(debitCardRequest.getCardType());
        debitCardEntity.setCardNumber(cardNumber);
        debitCardEntity.setCvv(cvv);
        debitCardEntity.setCardHolderName(account.getCustomer().getFullName());
        debitCardEntity.setExpiryDate(LocalDate.now().plusYears(4));
        debitCardEntity.setIssuedDate(LocalDate.now());
        debitCardEntity.addAccount(account);

        debitCardRepository.save(debitCardEntity);

        log.info("Debit card created successfully for account number {}", id);

        return true;
    }

    @Override
    public void blockDebitCard(String cardId) {
        // Logic to block a debit card
    }

    @Override
    public void unblockDebitCard(String cardId) {
        // Logic to unblock a debit card
    }

    @Override
    public void updateDebitCardDetails(String cardId, String newDetails) {
        // Logic to update debit card details
    }

    @Override
    public void getDebitCardDetails(String cardId) {
        // Logic to get debit card details
    }

    @Override
    public void deleteDebitCard(String cardId) {
        // Logic to delete a debit card
    }
}
