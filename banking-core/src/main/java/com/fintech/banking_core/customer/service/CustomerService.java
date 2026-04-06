package com.fintech.banking_core.customer.service;

import com.fintech.banking_core.customer.dto.CustomerDTO;
import com.fintech.banking_core.customer.domain.Register;

import java.util.List;

public interface CustomerService {
    CustomerDTO createCustomer(Register register);

    List<CustomerDTO> getAllCustomer();

    CustomerDTO updateCustomerDetails(CustomerDTO customerDto);

    String getCurrentUserLogin();

    void deleteCustomer(long id, String keycloakId);

    CustomerDTO getCustomerByKeycloakId();

    void logout();
}
