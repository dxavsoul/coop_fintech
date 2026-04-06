package com.fintech.banking_core.bank.controller;

import com.fintech.banking_core.bank.service.BankService;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/bank")
@RequiredArgsConstructor
@Validated
@Hidden
public class BankController {
    private final BankService bankService;

    @PostMapping("withdraw")
    public ResponseEntity withdraw(@Valid @RequestBody String transaction) {
        //bankService.withdraw(transaction);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("transfer")
    public ResponseEntity transfer(@Valid @RequestBody String transfer) {
        //bankService.transfer(transfer);
        return new ResponseEntity(HttpStatus.OK);
    }
}
