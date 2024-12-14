package com.mypay.banking.adapter.in.web;

import com.mypay.banking.application.port.in.FindBankAccountUseCase;
import com.mypay.banking.application.port.in.FindRegisteredBankAccountCommand;
import com.mypay.banking.domain.RegisteredBankAccount;
import com.mypay.common.annotation.WebAdapter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@WebAdapter
@RestController
@RequiredArgsConstructor
public class FindBankAccountController {

    private final FindBankAccountUseCase findBankAccountUseCase;

    @GetMapping("/banking/account/{registeredBankAccountId}")
    public ResponseEntity<RegisteredBankAccount> findRegisteredBankAccountByRegisteredBankAccountId(@PathVariable String registeredBankAccountId) throws BadRequestException {
        FindRegisteredBankAccountCommand command = FindRegisteredBankAccountCommand.builder()
                .registeredBankAccountId(registeredBankAccountId)
                .build();

        return ResponseEntity.ok(findBankAccountUseCase.findRegisteredBankAccount(command));
    }

}
