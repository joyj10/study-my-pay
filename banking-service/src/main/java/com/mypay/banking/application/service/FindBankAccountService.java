package com.mypay.banking.application.service;

import com.mypay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.mypay.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.mypay.banking.application.port.in.FindBankAccountUseCase;
import com.mypay.banking.application.port.in.FindRegisteredBankAccountCommand;
import com.mypay.banking.application.port.out.FindRegisteredBankAccountPort;
import com.mypay.banking.domain.RegisteredBankAccount;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FindBankAccountService implements FindBankAccountUseCase {

    private final FindRegisteredBankAccountPort findRegisteredBankAccountPort;
    private final RegisteredBankAccountMapper registeredBankAccountMapper;

    @Override
    public RegisteredBankAccount findRegisteredBankAccount(FindRegisteredBankAccountCommand command) throws BadRequestException {
        RegisteredBankAccountJpaEntity entity = findRegisteredBankAccountPort.findRegisteredBankAccount(new RegisteredBankAccount.RegisteredBankAccountId(command.getRegisteredBankAccountId()))
                .orElseThrow(() -> new BadRequestException("RegisteredBankAccount not found with ID: " + command.getRegisteredBankAccountId()));
        return registeredBankAccountMapper.mapToDomainEntity(entity);
    }
}
