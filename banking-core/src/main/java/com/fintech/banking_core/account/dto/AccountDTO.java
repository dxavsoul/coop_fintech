package com.fintech.banking_core.account.dto;

import com.fintech.banking_core.account.domain.Account;
import com.fintech.banking_core.common.enums.AccountStatus;
import com.fintech.banking_core.common.enums.AccountType;
import com.fintech.banking_core.customer.dto.CustomerDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO implements Serializable {

//    private Long id;

    @NotBlank(message = "Account number is required")
    private Long accountNumber;

    private CustomerDTO customer;

    private AccountType accountType;

    private AccountStatus status;

//    @PositiveOrZero(message = "Balance cannot be negative")
//    private BigDecimal balance;

//    @PositiveOrZero(message = "Available balance cannot be negative")
//    private BigDecimal availableBalance;

//    private String currency;

//    private BigDecimal interestRate;
//
//    private BigDecimal overdraftLimit;
//
//    private BigDecimal minimumBalance;


//    private LocalDateTime lastTransactionDate;
//
//    private boolean locked;
//
//    private String lockReason;

    // Conversion methods
    public static AccountDTO fromEntity(Account account) {
        return AccountDTO.builder()
//                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .customer(account.getCustomer() != null ? CustomerDTO.fromEntity(account.getCustomer()) : null)
                .accountType(account.getAccountType() != null ? account.getAccountType() : null)
                .status(account.getStatus() != null ? account.getStatus() : null)
//                .balance(account.getBalance())
//                .availableBalance(account.getAvailableBalance())
//                .currency(account.getCurrency())
//                .interestRate(account.getInterestRate())
//                .overdraftLimit(account.getOverdraftLimit())
//                .minimumBalance(account.getMinimumBalance())
//                .lastTransactionDate(account.getLastTransactionDate())
//                .locked(account.isLocked())
//                .lockReason(account.getLockReason())
                .build();
    }

    public Account toEntity() {
        Account account = new Account();
//        account.setId(this.id);
        account.setAccountNumber(this.accountNumber);
        if (this.customer != null) {
            account.setCustomer(this.customer.toEntity());
        }

        if (this.accountType != null) {
            try {
                account.setAccountType(this.accountType);
            } catch (IllegalArgumentException e) {
                account.setAccountType(AccountType.CHECKING);
            }
        }

        if (this.status != null) {
            try {
                account.setStatus(this.status);

            } catch (IllegalArgumentException e) {
                account.setStatus(AccountStatus.ACTIVE);
            }
        }

//        account.setBalance(this.balance);
//        account.setAvailableBalance(this.availableBalance);
//        account.setCurrency(this.currency);
//        account.setInterestRate(this.interestRate);
//        account.setOverdraftLimit(this.overdraftLimit);
//        account.setMinimumBalance(this.minimumBalance);

//        account.setLastTransactionDate(this.lastTransactionDate);
//        account.setLocked(this.locked);
//        account.setLockReason(this.lockReason);

        return account;
    }
}
