package com.fintech.banking_core.debitcard.dto;

import com.fintech.banking_core.common.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebitCardRequest {

    @NotNull
    private Long accountNumber; // Account number associated with the debit card

    @NotNull
    private CardType cardType; // Type of the debit card (e.g., VISA, MasterCard)
    // Date when the card expires
}
