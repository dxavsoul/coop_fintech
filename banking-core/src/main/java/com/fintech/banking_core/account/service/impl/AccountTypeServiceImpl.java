package com.fintech.banking_core.account.service.impl;

import com.fintech.banking_core.account.domain.AccountType;
import com.fintech.banking_core.account.repository.AccountRepository;
import com.fintech.banking_core.account.repository.AccountTypeRepository;
import com.fintech.banking_core.account.service.AccountTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountTypeServiceImpl implements AccountTypeService {
    private final AccountTypeRepository accountTypeRepository;
    private ModelMapper modelMapper;

    @Override
    public List<AccountType> getAllAccountTypes() {
        log.trace("getAllAccountTypes");
        log.debug("Fetching all account types");
        List<AccountType> accountTypes = accountTypeRepository.findAllActive();
        if (accountTypes.isEmpty()) {
            log.warn("No account types found");
            return List.of();
        }
        log.info("Found {} account types", accountTypes.size());

        return accountTypes;
    }

    @Override
    public AccountType getAccountTypeById(Long id) {
        return null;
    }

    @Override
    public AccountType getAccountTypeByName(String name) {
        return null;
    }

    @Override
    public AccountType createAccountType(AccountType accountType) {
        log.trace("createAccountType");
        log.debug("Creating account type with name: {}", accountType.getName());
        if (accountTypeRepository.findByName(accountType.getName())) {
            log.error("Account type with name {} already exists", accountType.getName());
            throw new RuntimeException("Account type already exists");
        }
        accountTypeRepository.save(accountType);
        log.info("Account type {} created successfully", accountType.getName());
        return accountType;
    }

    @Override
    public void updateAccountType(Long id, AccountType accountType) {

    }

    @Override
    public void deleteAccountType(Long id) {

    }
}
