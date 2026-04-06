package com.fintech.banking_core.customer.service.impl;

import com.fintech.banking_core.account.service.AccountService;
import com.fintech.banking_core.customer.domain.Customer;
import com.fintech.banking_core.customer.dto.CustomerDTO;
import com.fintech.banking_core.customer.domain.Register;
import com.fintech.banking_core.customer.repository.CustomerRepository;
import com.fintech.banking_core.customer.service.CustomerService;
import io.jsonwebtoken.lang.Collections;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RolesResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
    @Value("${keycloak.realm}")
    private String realm;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AccountService accountService;
    private final CustomerRepository customerRepository;
    private final Keycloak keycloak;

    @SneakyThrows
    @Override
    public CustomerDTO createCustomer(Register register) {
        log.trace("Entering method create Customer");
        String userId = "";
        //UserRecord firebaseUser = null;
        UsersResource userResource = getUsersResource();
        try {
            CustomerDTO customerDTO = register.getCustomer();

            Response keycloakResponse = createKeycloakUser(customerDTO);
            //firebaseUser = firebaseService.createUser(customerDTO);
//            RealmResource realmResource = getRealmResource();
//            RoleRepresentation roleRepresentation = realmResource.roles().get("customer").toRepresentation();

            RolesResource rolesResource = getRolesResource();
            RoleRepresentation customerRoleRepresentation = rolesResource.get("customer").toRepresentation();

            if (keycloakResponse.getStatus() == 201) {
                userId = CreatedResponseUtil.getCreatedId(keycloakResponse);

                userResource.get(userId).roles().realmLevel().add(Collections.of(customerRoleRepresentation));
                Customer customerDtoToEntity = convertCustomerDtoToEntity(customerDTO);


                //customerDtoToEntity.setFirebaseId(firebaseUser.getUid());
                customerDtoToEntity.setKeycloakId(userId);
                customerDtoToEntity.setPassword(encodePassword(customerDTO.getPassword()));

                var account = accountService.createAccount(register.getAccountType());
                customerDtoToEntity.addAccount(account);

                Customer saved = customerRepository.save(customerDtoToEntity);

                log.info("Customer saved: {}", saved);

                return convertCustomerEntityToDto(saved);
            } else
                return null;

        } catch (RuntimeException e) {
            log.error("Customer already exists: {}", e.getMessage());
            userResource.get(userId).remove();
            //firebaseService.deleteUser(firebaseUser.getUid());
            return null;
        }
    }

    @Override
    public List<CustomerDTO> getAllCustomer() {
        return List.of();
    }

    @Override
    public CustomerDTO updateCustomerDetails(CustomerDTO customerDto) {
        return null;
    }

    @Override
    public String getCurrentUserLogin() {
        return "";
    }

    @Override
    public void deleteCustomer(long id, String keycloakId) {

    }

    @Override
    public CustomerDTO getCustomerByKeycloakId() {
        return null;
    }

    @Override
    public void logout() {

    }

    private CustomerDTO convertCustomerEntityToDto(Customer customer) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(customer, CustomerDTO.class);
    }

    private Customer convertCustomerDtoToEntity(CustomerDTO customerDTO) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
        return modelMapper.map(customerDTO, Customer.class);
    }

    private String encodePassword(String password) {
        // Implement your password encoding logic here
        return passwordEncoder.encode(password); // Placeholder, replace with actual encoding
    }

    private Response createKeycloakUser(CustomerDTO user) {
        log.trace("Entering method create createKeycloakUser");
        try {
            log.debug("Creating keycloak customer for " + user.getEmail());
            // Implement Keycloak user creation logic here
            //CredentialRepresentation credentialRepresentation = createPasswordCrendentials(user.getPassword());
            UserRepresentation kcUser = new UserRepresentation();
            kcUser.setEnabled(true);
            kcUser.setFirstName(user.getFirstName());
            kcUser.setLastName(user.getFirstName());
            kcUser.setUsername(user.getEmail());
            kcUser.setEmail(user.getEmail());
            kcUser.setEmailVerified(true);

            CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
            credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
            credentialRepresentation.setValue(user.getPassword());
            credentialRepresentation.setTemporary(false);

            kcUser.setCredentials(List.of(credentialRepresentation));
            //kcUser.setCredentials(Collections.singletonList(credentialRepresentation));

            UsersResource userResource = getUsersResource(); //getKeycloakUserResource().get(user.getEmail());

            Response response = userResource.create(kcUser);
            log.info("Keycloak user creation response: {}", response.getStatus());
            if (response.getStatus() != 201) {
                log.info("error {} happened while creating keycloak user {}", response.getStatus(), user.getEmail());
                throw new RuntimeException("Failed to create user in Keycloak " + response.getStatus());
            }
            log.info("Keycloak user created successfully: {}", user.getEmail());
            return response; // Placeholder, replace with actual response
        } catch (Exception e) {
            log.error("Error creating keycloak user: {}", e.getMessage());
            throw new RuntimeException("Error creating keycloak user", e);
        }
    }

    private UsersResource getUsersResource() {
        return keycloak.realm(realm).users();
    }

    private RolesResource getRolesResource() {
        return keycloak.realm(realm).roles();
    }

//    private CredentialRepresentation createPasswordCrendentials (String password) {
//        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
//        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
//        credentialRepresentation.setValue(password);
//        credentialRepresentation.setTemporary(false);
//        return credentialRepresentation;
//    }
}
