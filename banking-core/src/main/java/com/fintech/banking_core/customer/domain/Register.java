package com.fintech.banking_core.customer.domain;

import com.fintech.banking_core.common.enums.AccountType;
import com.fintech.banking_core.customer.dto.CustomerDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Register {

    @Valid
    private CustomerDTO customer;

    @NotNull
    private AccountType accountType;
}
