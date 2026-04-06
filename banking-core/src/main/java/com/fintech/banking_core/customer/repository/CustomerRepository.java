package com.fintech.banking_core.customer.repository;

import com.fintech.banking_core.customer.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT * FROM customer c WHERE c.email = ?1", nativeQuery = true)
    <Optional> Customer findByEmail(String email);

    Customer findByKeycloakId(String id);
}
