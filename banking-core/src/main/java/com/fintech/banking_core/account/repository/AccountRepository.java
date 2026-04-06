package com.fintech.banking_core.account.repository;

import com.fintech.banking_core.account.domain.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(value= "SELECT account_balance FROM account a WHERE a.acct_num = ?1", nativeQuery = true)
    BigDecimal getCustomerBalance(Account account);

    @Query(value= "SELECT account_balance FROM account a WHERE a.acct_num = ?1", nativeQuery = true)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findByAcctNum(String acctNum);
}
