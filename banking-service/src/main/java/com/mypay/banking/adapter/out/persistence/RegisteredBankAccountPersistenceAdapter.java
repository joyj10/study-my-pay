package com.mypay.banking.adapter.out.persistence;

import com.mypay.banking.application.port.in.GetRegisteredBankAccountCommand;
import com.mypay.banking.application.port.out.FindRegisteredBankAccountPort;
import com.mypay.banking.application.port.out.GetRegisteredBankAccountPort;
import com.mypay.common.annotation.PersistenceAdapter;
import com.mypay.banking.application.port.out.RegisterBankAccountPort;
import com.mypay.banking.domain.RegisteredBankAccount;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@PersistenceAdapter
@RequiredArgsConstructor
public class RegisteredBankAccountPersistenceAdapter implements RegisterBankAccountPort, FindRegisteredBankAccountPort, GetRegisteredBankAccountPort {

    private final SpringDataRegisteredBankAccountRepository bankAccountRepository;

    @Override
    public RegisteredBankAccountJpaEntity createRegisteredBankAccount(RegisteredBankAccount.MembershipId membershipId,
                                                                      RegisteredBankAccount.BankName bankName,
                                                                      RegisteredBankAccount.BankAccountNumber bankAccountNumber,
                                                                      RegisteredBankAccount.LinkedStatusIsValid linkedStatusIsValid,
                                                                      RegisteredBankAccount.AggregateIdentifier aggregateIdentifier) {
        return bankAccountRepository.save(
                new RegisteredBankAccountJpaEntity(
                        membershipId.getMembershipId(),
                        bankName.getBankName(),
                        bankAccountNumber.getBankAccountNumber(),
                        linkedStatusIsValid.isLinkedStatusIsValid(),
                        aggregateIdentifier.getAggregateIdentifier()
                )
        );
    }

    @Override
    public Optional<RegisteredBankAccountJpaEntity> findRegisteredBankAccount(RegisteredBankAccount.RegisteredBankAccountId registeredBankAccountId) {
        return bankAccountRepository.findById(Long.parseLong(registeredBankAccountId.getRegisteredBankAccountId()));
    }

    @Override
    public RegisteredBankAccountJpaEntity getRegisteredBankAccount(GetRegisteredBankAccountCommand command) {
        List<RegisteredBankAccountJpaEntity> entityList = bankAccountRepository.findByMembershipId(command.getMembershipId());
        if (entityList.size() > 0) {
            return entityList.get(0);
        }
        return null;
    }
}
