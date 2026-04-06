package com.fintech.banking_core.account.service.impl;

import com.fintech.banking_core.account.domain.Account;
import com.fintech.banking_core.account.dto.AccountDTO;
import com.fintech.banking_core.account.repository.AccountRepository;
import com.fintech.banking_core.account.service.AccountService;
import com.fintech.banking_core.common.enums.AccountStatus;
import com.fintech.banking_core.common.enums.AccountType;
import com.fintech.banking_core.customer.domain.Customer;
import com.fintech.banking_core.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    @Override
    public AccountDTO getAccountByAccountNumber(String accountId) {
        log.trace("Entering method getCustomerByAccountNumber");
        log.debug("Getting customer with account number --> {} ", accountId);

        return accountRepository.findById(Long.valueOf(accountId))
                .map(this::convertAccountEntitytoDTO)
                .orElseThrow(() -> {
                    String message = "No account number found for id: ".concat(accountId).concat(" ");
                    log.error(message);
                    return new RuntimeException(message);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream().map(this::convertAccountEntitytoDTO).collect(Collectors.toList());
    }

    @Override
    public Account createAccount(AccountType accountType) {
        log.trace("Entering method createAccount");
        try {
            log.debug("Creating account with type --> {} ", accountType);
            Account acc=new Account();
            acc.setAccountNumber(generateAccountNumber());
            acc.setNubanNo(String.valueOf(acc.getAccountNumber()));
            acc.setAccountType(accountType);
            acc.setStatus(AccountStatus.ACTIVE);
            acc.setAccountBalance(BigDecimal.valueOf(0));
            return acc;
        } catch (Exception e) {
            log.error("Error creating account: {}", e.getMessage());
            throw new RuntimeException("Error creating account");
        }
    }

    @Override
    public AccountDTO createAccount(Long customerID, AccountType accountType) {
        Customer cust = customerRepository.findById(customerID)
                .orElseThrow(() -> new RuntimeException("No customer with account number: " + customerID));
        Account newAccount = new Account();
        newAccount.setAccountNumber(generateAccountNumber());
        newAccount.setNubanNo(String.valueOf(newAccount.getAccountNumber()));
        newAccount.setAccountType(accountType);
        newAccount.setCustomer(cust);
        newAccount.setStatus(AccountStatus.ACTIVE);
        //newAccount.setBalance(BigDecimal.valueOf(0));
        accountRepository.save(newAccount);
        return null;
    }

    @Override
    public void deleteAccount(Long accountId) {

    }

    private AccountDTO convertAccountEntitytoDTO(Account account) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(account, AccountDTO.class);
    }

    public long generateAccountNumber(){
        ThreadLocalRandom random = ThreadLocalRandom.current();

        long accNumber =  random.nextLong(10_000_000_000L, 100_000_000_000L);

        while (accountRepository.existsById(accNumber))
            accNumber=random.nextLong(10_000_000_000L, 100_000_000_000L);

        return accNumber;
    }
}
