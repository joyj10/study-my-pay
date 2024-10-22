package com.mypay.banking.application.port.in;

import com.mypay.banking.domain.RegisteredBankAccount;
import com.mypay.common.UseCase;
import org.apache.coyote.BadRequestException;

@UseCase
public interface FindBankAccountUseCase {
    RegisteredBankAccount findRegisteredBankAccount(FindRegisteredBankAccountCommand command) throws BadRequestException;
}
