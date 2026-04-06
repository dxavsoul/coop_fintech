package com.fintech.banking_core.debitcard.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fintech.banking_core.account.domain.Account;
import com.fintech.banking_core.common.enums.CardStatus;
import com.fintech.banking_core.common.enums.CardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebitCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "debitCard", cascade = {
            CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
    })
    @JsonBackReference
    private Set<Account> accounts;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(nullable = false, unique = true)
    private String cardNumber;

    @Column(nullable = false)
    private String cardHolderName;

    @Column(nullable = false)
    private int cvv;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDate issuedDate;

    @Column(nullable = false)
    private LocalDate expiryDate;

    @UpdateTimestamp
    private Date lastActivity;


    public void addAccount(Account account) {
        if (account != null)
            if (this.accounts == null)
                accounts = new HashSet<>();
        this.accounts.add(account);
        account.setDebitCard(this);
    }
}
