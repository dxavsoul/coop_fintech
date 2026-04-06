package com.fintech.banking_core.account.service;

import com.fintech.banking_core.account.domain.Account;
import com.fintech.banking_core.account.dto.AccountDTO;
import com.fintech.banking_core.common.enums.AccountType;

import java.util.List;

public interface AccountService {
    AccountDTO getAccountByAccountNumber(String accountId);
    List<AccountDTO> getAllAccounts();
    Account createAccount(AccountType accountType);
    AccountDTO createAccount(Long customerID, AccountType accountType);
    void deleteAccount(Long accountId);
}
