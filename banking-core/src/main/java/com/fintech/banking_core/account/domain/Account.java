package com.fintech.banking_core.account.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fintech.banking_core.common.enums.AccountStatus;
import com.fintech.banking_core.common.enums.AccountType;
import com.fintech.banking_core.customer.domain.Customer;
import com.fintech.banking_core.debitcard.domain.DebitCard;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account implements Serializable {

    @Id
    @Column(name = "account_number", unique = true, nullable = false, updatable = false)
    private Long accountNumber;

    @Column(unique = true)
    private String nubanNo;

    @Column(name = "balance",nullable = false)
    private BigDecimal accountBalance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @ManyToOne(cascade = {
            CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},
            fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    @JsonBackReference
    private Customer customer;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn
    @JsonBackReference
    private DebitCard debitCard;

    @CreationTimestamp
    private Date dateCreated;

    @UpdateTimestamp
    private  Date lastActivity;

}
