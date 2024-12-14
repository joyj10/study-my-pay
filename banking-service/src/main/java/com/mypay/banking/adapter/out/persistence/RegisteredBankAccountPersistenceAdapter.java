package com.mypay.banking.adapter.out.persistence;

import com.mypay.banking.application.port.out.FindRegisteredBankAccountPort;
import com.mypay.common.annotation.PersistenceAdapter;
import com.mypay.banking.application.port.out.RegisterBankAccountPort;
import com.mypay.banking.domain.RegisteredBankAccount;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort, FindRegisteredBankAccountPort {

    private final SpringDataRegisteredBankAccountRepository bankAccountRepository;

    @Override
    public RegisteredBankAccountJpaEntity createRegisteredBankAccount(RegisteredBankAccount.MembershipId membershipId,
                                                                      RegisteredBankAccount.BankName bankName,
                                                                      RegisteredBankAccount.BankAccountNumber bankAccountNumber,
                                                                      RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid) {
        return bankAccountRepository.save(
                new RegisteredBankAccountJpaEntity(
                        membershipId.getMembershipId(),
                        bankName.getBankName(),
                        bankAccountNumber.getBankAccountNumber(),
                        linkedStatusIsValid.isLinkedStatusIsValid()
                )
        );
    }

    @Override
    public Optional<RegisteredBankAccountJpaEntity> findRegisteredBankAccount(RegisteredBankAccount.RegisteredBankAccountId registeredBankAccountId) {
        return bankAccountRepository.findById(Long.parseLong(registeredBankAccountId.getRegisteredBankAccountId()));
    }
}
