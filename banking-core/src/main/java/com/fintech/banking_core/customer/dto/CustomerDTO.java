package com.fintech.banking_core.customer.dto;

import com.fintech.banking_core.common.enums.AccountType;
import com.fintech.banking_core.common.enums.CustomerStatus;
import com.fintech.banking_core.common.enums.DocumentType;
import com.fintech.banking_core.customer.domain.Customer;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO implements Serializable {

    private Long id;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password is required")
    private String password;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone number is invalid")
    private String phoneNumber;

    @NotNull
    @NotBlank(message = "Date of birth is required")
    private LocalDate dateOfBirth;

    @NotBlank(message = "Address is required")
    private String address;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private String firebaseId;

    private AccountType accountType;

    @NotNull(message = "ID document type is required")
    private DocumentType documentType;

    @NotBlank(message = "ID document number is required")
    private String idDocumentNumber;

    private CustomerStatus status;



    // Conversion methods
    public static CustomerDTO fromEntity(Customer customer) {
        return CustomerDTO.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .dateOfBirth(customer.getDateOfBirth())
                .address(customer.getAddress())
                .city(customer.getCity())
                .state(customer.getState())
                .postalCode(customer.getPostalCode())
                .firebaseId(customer.getFirebaseId())
                .country(customer.getCountry())
                .documentType(customer.getDocumentType())
                .idDocumentNumber(customer.getIdDocumentNumber())
                .status(customer.getStatus())
                .build();
    }

    public Customer toEntity() {
        Customer customer = new Customer();
        customer.setId(this.id);
        customer.setFirstName(this.firstName);
        customer.setLastName(this.lastName);
        customer.setEmail(this.email);
        customer.setPhoneNumber(this.phoneNumber);
        customer.setDateOfBirth(this.dateOfBirth);
        customer.setAddress(this.address);
        customer.setCity(this.city);
        customer.setState(this.state);
        customer.setPostalCode(this.postalCode);
        customer.setCountry(this.country);
        customer.setFirebaseId(this.firebaseId);
        customer.setDocumentType(this.documentType);
        customer.setIdDocumentNumber(this.idDocumentNumber);

        if (this.status != null) {
            try {
                customer.setStatus(CustomerStatus.valueOf(this.status.name()));
            } catch (IllegalArgumentException e) {
                customer.setStatus(CustomerStatus.ACTIVE);
            }
        }

        return customer;
    }
}
