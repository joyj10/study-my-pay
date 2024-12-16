package com.mypay.banking.application.service;

import com.mypay.banking.adapter.axon.command.CreateRegisteredBankAccountCommand;
import com.mypay.banking.adapter.out.external.bank.BankAccount;
import com.mypay.banking.adapter.out.external.bank.GetBankAccountRequest;
import com.mypay.banking.adapter.out.persistence.RegisteredBankAccountJpaEntity;
import com.mypay.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.mypay.banking.application.port.in.GetRegisteredBankAccountCommand;
import com.mypay.banking.application.port.in.GetRegisteredBankAccountUseCase;
import com.mypay.banking.application.port.in.RegisterBankAccountCommand;
import com.mypay.banking.application.port.in.RegisterBankAccountUseCase;
import com.mypay.banking.application.port.out.*;
import com.mypay.banking.domain.RegisteredBankAccount;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegisterBankAccountService implements RegisterBankAccountUseCase, GetRegisteredBankAccountUseCase {

    private final RegisterBankAccountPort registerBankAccountPort;
    private final RegisteredBankAccountMapper registeredBankAccountMapper;
    private final RequestBankAccountInfoPort requestBankAccountInfoPort;
    private final GetMembershipPort getMembershipPort;

    private final GetRegisteredBankAccountPort getRegisteredBankAccountPort;
    private final CommandGateway commandGateway;

    @Override
    public RegisteredBankAccount registerBankAccount(RegisterBankAccountCommand command) {
        // 은행 계좌를 등록해야 하는 서비스 (비즈니스 로직)

        // call membership svc, 정산인지 확인
        MembershipStatus membershipStatus = getMembershipPort.getMembership(command.getMembershipId());
        if (!membershipStatus.isValid()) {
            return null;
        }
        // call external bank svc, 정상인지 확인

        // 1. 외부 실제 은행에 등록 가능한 계좌 인지(정상 여부) 확인: 외부 은행에 정상 계좌 인지 확인
        // Biz Logic -> External System
        // Port -> Adapter -> External System
        // Port

        // 실제 외부의 은행 계좌 정보를 Get
        BankAccount accountInfo = requestBankAccountInfoPort.getBankAccountInfo(new GetBankAccountRequest(command.getBankName(), command.getBankAccountNumber()));
        boolean accountIsValid =  accountInfo.isValid();

        // 2. 등록 가능한 계좌 시, 등록 -> 성공 시 등록에 성공한 등록 정보 리턴
        // 2-1. 등록 가능 하지 않은 계좌 -> 에러 리턴
        if (accountIsValid) {
            // 등록 정보 저장
            RegisteredBankAccountJpaEntity savedAccountInfo = registerBankAccountPort.createRegisteredBankAccount(
                    new RegisteredBankAccount.MembershipId(command.getMembershipId()),
                    new RegisteredBankAccount.BankName(command.getBankName()),
                    new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                    new RegisteredBankAccount.LinkedStatusIsValid(command.isValid()),
                    new RegisteredBankAccount.AggregateIdentifier("")
            );
            return registeredBankAccountMapper.mapToDomainEntity(savedAccountInfo);
        } else {
            // 추후 예외 처리 필요
            return null;
        }
    }

    @Override
    public void registerBankAccountByEvent(RegisterBankAccountCommand command) {
        commandGateway.send(new CreateRegisteredBankAccountCommand(command.getMembershipId(), command.getBankName(), command.getBankAccountNumber()))
                .whenComplete(
                        (result, throwable) -> {
                            if(throwable != null) {
                                throwable.printStackTrace();
                            } else {
                                // 정상적으로 이벤트 소싱.
                                // -> registeredBankAccount 를 insert
                                registerBankAccountPort.createRegisteredBankAccount(
                                        new RegisteredBankAccount.MembershipId(command.getMembershipId()+""),
                                        new RegisteredBankAccount.BankName(command.getBankName()),
                                        new RegisteredBankAccount.BankAccountNumber(command.getBankAccountNumber()),
                                        new RegisteredBankAccount.LinkedStatusIsValid(command.isValid()),
                                        new RegisteredBankAccount.AggregateIdentifier(result.toString()));
                            }
                        }
                );
    }

    @Override
    public RegisteredBankAccount getRegisteredBankAccount(GetRegisteredBankAccountCommand command) {
        return registeredBankAccountMapper.mapToDomainEntity(getRegisteredBankAccountPort.getRegisteredBankAccount(command));
    }
}
