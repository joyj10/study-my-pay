package com.mypay.banking.application.service;

import com.mypay.banking.adapter.axon.command.CreateFirmbankingRequestCommand;
import com.mypay.banking.adapter.axon.command.UpdateFirmbankingRequestCommand;
import com.mypay.banking.adapter.out.external.bank.ExternalFirmBankingRequest;
import com.mypay.banking.adapter.out.external.bank.FirmBankingResult;
import com.mypay.banking.adapter.out.persistence.FirmBankingRequestJpaEntity;
import com.mypay.banking.adapter.out.persistence.FirmBankingRequestMapper;
import com.mypay.banking.adapter.out.persistence.RegisteredBankAccountMapper;
import com.mypay.banking.adapter.out.persistence.SpringDataFirmBankingRequestRepository;
import com.mypay.banking.application.port.in.RequestFirmBankingCommand;
import com.mypay.banking.application.port.in.RequestFirmBankingUseCase;
import com.mypay.banking.application.port.in.UpdateFirmbankingCommand;
import com.mypay.banking.application.port.in.UpdateFirmbankingUseCase;
import com.mypay.banking.application.port.out.RegisterBankAccountPort;
import com.mypay.banking.application.port.out.RequestBankAccountInfoPort;
import com.mypay.banking.application.port.out.RequestExternalFirmBakingPort;
import com.mypay.banking.application.port.out.RequestFirmBankingPort;
import com.mypay.banking.domain.FirmBankingRequest;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestFirmBankingService implements RequestFirmBankingUseCase, UpdateFirmbankingUseCase {

    private final FirmBankingRequestMapper firmBankingRequestMapper;
    private final RequestFirmBankingPort requestFirmBankingPort;

    private final RequestExternalFirmBakingPort requestExternalFirmBakingPort;
    private final CommandGateway commandGateway;

    @Override
    public FirmBankingRequest requestFirmBanking(RequestFirmBankingCommand command) {
        // Business Login
        // a -> b 계좌

        // 1. 요청에 대해 정보 먼저 write (요청 상태)
        FirmBankingRequestJpaEntity entity = requestFirmBankingPort.createFirmBankingRequest(
                new FirmBankingRequest.FromBankName(command.getFromBankName()),
                new FirmBankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                new FirmBankingRequest.ToBankName(command.getToBankName()),
                new FirmBankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                new FirmBankingRequest.MoneyAmount(command.getMoneyAmount()),
                new FirmBankingRequest.FirmBankingStatus(0),
                new FirmBankingRequest.FirmbankingAggregateIdentifier("")
        );

        // 2. 외부 은행에 펌뱅킹 요청
        FirmBankingResult result = requestExternalFirmBakingPort.requestExternalFirmBanking(
                new ExternalFirmBankingRequest(
                        command.getFromBankName(),
                        command.getFromBankAccountNumber(),
                        command.getToBankName(),
                        command.getToBankAccountNumber()
                )
        );

        // Transactional UUID (디버깅 시 사용)
        UUID randomUUID = UUID.randomUUID();
        entity.setUuid(randomUUID.toString());

        // 3. 결과에 따라서 1번에 작성 한 FirmBankingRequest 정보 업데이트
        if (200 == result.getResultCode()) {
            // 성공
            entity.setFirmBankingStatus(1);
        } else {
            // 실패
            entity.setFirmBankingStatus(2);
        }

        // 4. 바뀐 상태값 저장 후 결과 리턴
        FirmBankingRequestJpaEntity savedEntity = requestFirmBankingPort.modifyFirmBankingRequest(entity);
        return firmBankingRequestMapper.mapToDomainEntity(savedEntity, randomUUID);
    }

    @Override
    public void requestFirmBankingByEvent(RequestFirmBankingCommand command) {
        CreateFirmbankingRequestCommand createFirmbankingRequestCommand = CreateFirmbankingRequestCommand.builder()
                .toBankName(command.getToBankName())
                .toBankAccountNumber(command.getToBankAccountNumber())
                .fromBankName(command.getFromBankName())
                .fromBankAccountNumber(command.getFromBankAccountNumber())
                .moneyAmount(command.getMoneyAmount())
                .build();

        commandGateway.send(createFirmbankingRequestCommand).whenComplete(
                (result, throwable) -> {
                    if (throwable != null){
                        // 실패
                        throwable.printStackTrace();
                    } else {
                        System.out.println("createFirmbankingRequestCommand completed, Aggregate ID: " + result.toString());

                        // Request Firmbanking 의 DB save
                        FirmBankingRequestJpaEntity requestedEntity = requestFirmBankingPort.createFirmBankingRequest(
                                new FirmBankingRequest.FromBankName(command.getFromBankName()),
                                new FirmBankingRequest.FromBankAccountNumber(command.getFromBankAccountNumber()),
                                new FirmBankingRequest.ToBankName(command.getToBankName()),
                                new FirmBankingRequest.ToBankAccountNumber(command.getToBankAccountNumber()),
                                new FirmBankingRequest.MoneyAmount(command.getMoneyAmount()),
                                new FirmBankingRequest.FirmBankingStatus(0),
                                new FirmBankingRequest.FirmbankingAggregateIdentifier(result.toString())
                        );

                        // 은행에 펌뱅킹 요청
                        FirmBankingResult firmbankingResult = requestExternalFirmBakingPort.requestExternalFirmBanking(new ExternalFirmBankingRequest(
                                command.getFromBankName(),
                                command.getFromBankAccountNumber(),
                                command.getToBankName(),
                                command.getToBankAccountNumber()
                        ));

                        // 결과에 따라서 DB save
                        // 3. 결과에 따라서 1번에서 작성했던 FirmbankingRequest 정보를 Update
                        if (firmbankingResult.getResultCode() == 0){
                            // 성공
                            requestedEntity.setFirmBankingStatus(1);
                        } else {
                            // 실패
                            requestedEntity.setFirmBankingStatus(2);
                        }

                        requestFirmBankingPort.modifyFirmBankingRequest(requestedEntity);
                    }
                }
        );
        // Command -> Event Sourcing
    }

    @Override
    public void updateFirmBankingByEvent(UpdateFirmbankingCommand command) {
        // command.
        UpdateFirmbankingRequestCommand updateFirmbankingRequestCommand =
                new UpdateFirmbankingRequestCommand(command.getFirmbankingAggregateIdentifier(), command.getFirmbankingStatus());

        commandGateway.send(updateFirmbankingRequestCommand)
                .whenComplete((result, throwable) -> {
                    if (throwable != null){
                        // 실패
                        throwable.printStackTrace();
                    } else {
                        System.out.println("updateFirmbankingRequestCommand completed, Aggregate ID: " + result.toString());
                        FirmBankingRequestJpaEntity entity = requestFirmBankingPort.getFirmBankingRequest(
                                new FirmBankingRequest.FirmbankingAggregateIdentifier(command.getFirmbankingAggregateIdentifier()));

                        // status 의 변경으로 인한 외부 은행과의 커뮤니케이션
                        // if rollback -> 0, status 변경도 해주겠지만
                        // + 기존 펌뱅킹 정보에서 from <-> to 가 변경된 펌뱅킹을 요청하는 새로운 요청
                        entity.setFirmBankingStatus(command.getFirmbankingStatus());
                        requestFirmBankingPort.modifyFirmBankingRequest(entity);
                    }
                });
    }
}
