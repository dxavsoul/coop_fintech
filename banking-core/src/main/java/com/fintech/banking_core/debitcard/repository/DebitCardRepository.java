package com.fintech.banking_core.debitcard.repository;

import com.fintech.banking_core.debitcard.domain.DebitCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebitCardRepository extends JpaRepository<DebitCard, Long> {
    // This interface extends JpaRepository, which provides CRUD operations for the DebitCard entity.
}
