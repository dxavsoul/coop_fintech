package com.fintech.banking_core.account.service;

import com.fintech.banking_core.account.domain.AccountType;

import java.util.List;

public interface AccountTypeService {
    List<AccountType> getAllAccountTypes();

    AccountType getAccountTypeById(Long id);

    AccountType getAccountTypeByName(String name);

    AccountType createAccountType(AccountType accountType);

    void updateAccountType(Long id, AccountType accountType);

    void deleteAccountType(Long id);
}
