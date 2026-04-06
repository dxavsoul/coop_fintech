package com.fintech.banking_core.account.repository;

import com.fintech.banking_core.account.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountTypeRepository extends JpaRepository<AccountType, Long> {

    @Query(value = "SELECT * FROM account_types WHERE status = 'ACTIVE'", nativeQuery = true)
    List<AccountType> findAllActive();

    @Query(value = "SELECT * FROM account_types WHERE name = ?1 AND status = 'ACTIVE'", nativeQuery = true)
    boolean findByName(String name);


}
