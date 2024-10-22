package com.mypay.banking.application.port.out;

import com.mypay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.mypay.banking.domain.RegisteredBankAccount;

import java.util.Optional;

public interface FindRegisteredBankAccountPort {
    Optional<RegisteredBankAccountJpaEntity> findRegisteredBankAccount(RegisteredBankAccount.RegisteredBankAccountId registeredBankAccountId);
}
