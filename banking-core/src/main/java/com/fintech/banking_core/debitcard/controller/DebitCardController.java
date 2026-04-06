package com.fintech.banking_core.debitcard.controller;

import com.fintech.banking_core.debitcard.dto.DebitCardRequest;
import com.fintech.banking_core.debitcard.service.DebitCardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/debitcard")
@RequiredArgsConstructor
public class DebitCardController {
    private final DebitCardService debitCardService;

    @PostMapping("cardRequest")
    @Operation(summary = "Request a debit card", description = "Request a new debit card for an account", tags = "Debit Card")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Debit card request was successful",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DebitCardRequest.class))})})
    public ResponseEntity createDebitCard(@Valid @RequestBody DebitCardRequest debitCardRequest) {
        var result = debitCardService.requestDebitCard(debitCardRequest);
        if (!result) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
