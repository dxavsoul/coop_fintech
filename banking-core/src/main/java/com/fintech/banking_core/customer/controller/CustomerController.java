package com.fintech.banking_core.customer.controller;

import com.fintech.banking_core.customer.dto.CustomerDTO;
import com.fintech.banking_core.customer.domain.Register;
import com.fintech.banking_core.customer.service.CustomerService;
import com.fintech.banking_core.services.KeycloakService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;
    private final KeycloakService keycloakService;

    @PostMapping("register")
    @Operation(summary = "Create a new customer", description = "Register a new customer account", tags = "Customer")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody Register register) {
        CustomerDTO saveUser = customerService.createCustomer(register);
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }

    @PutMapping("forgot-password")
    @Operation(summary = "Forgot password", description = "Send a forgot password email to the customer", tags = "Customer")
    public ResponseEntity<Void> forgotPassword(@RequestParam String username) {
        keycloakService.forgotPassword(username);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}