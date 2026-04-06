package com.fintech.banking_core.account.controller;

import com.fintech.banking_core.account.domain.AccountType;
import com.fintech.banking_core.account.service.AccountTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account-types")
@RequiredArgsConstructor
public class AccountTypeController {
    private final AccountTypeService accountTypeService;

    @PostMapping("list")
    public List<AccountType> getAllAccountTypes() {
        return accountTypeService.getAllAccountTypes();
    }

    @PostMapping("create")
    public AccountType createAccountType(AccountType accountType) {
        return accountTypeService.createAccountType(accountType);
    }
}
