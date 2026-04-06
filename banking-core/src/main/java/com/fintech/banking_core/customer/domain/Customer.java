package com.fintech.banking_core.customer.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fintech.banking_core.account.domain.Account;
import com.fintech.banking_core.common.enums.CustomerStatus;
import com.fintech.banking_core.common.enums.DocumentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, updatable = false)
    private String keycloakId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number is invalid")
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "country")
    private String country;

    @Column(name = "firebase_id")
    private String firebaseId;

    @Column(name = "id_document_type")
    private DocumentType documentType; // passport, national ID, etc.

    @Column(name = "id_document_number", unique = true)
    private String idDocumentNumber;

    @Column(name = "customer_status")
    @Enumerated(EnumType.STRING)
    private CustomerStatus status = CustomerStatus.ACTIVE;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Account> accounts;

    public void addAccount(Account account) {
        if (account != null) {
            if (this.accounts == null) {
                accounts = new HashSet<>();
            }
            accounts.add(account);
            account.setCustomer(this);
        }
    }

    //Return Customer First Name and Last Name
    public String getFullName() {
        return firstName + " " + lastName;
    }
}
