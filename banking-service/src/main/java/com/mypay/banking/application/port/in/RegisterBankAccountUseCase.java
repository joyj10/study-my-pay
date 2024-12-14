package com.mypay.banking.application.port.in;

import com.mypay.banking.domain.RegisteredBankAccount;
import com.mypay.common.annotation.UseCase;

@UseCase
public interface RegisterBankAccountUseCase {
    RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command);
}
