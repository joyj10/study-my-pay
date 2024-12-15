package com.mypay.banking.application.port.out;

import com.mypay.banking.adapter.out.persistence.FirmBankingRequestJpaEntity;
import com.mypay.banking.domain.FirmBankingRequest;

public interface RequestFirmBankingPort {
    FirmBankingRequestJpaEntity createFirmBankingRequest(
            FirmBankingRequest.FromBankName fromBankName,
            FirmBankingRequest.FromBankAccountNumber fromBankAccountNumber,
            FirmBankingRequest.ToBankName toBankName,
            FirmBankingRequest.ToBankAccountNumber toBankAccountNumber,
            FirmBankingRequest.MoneyAmount moneyAmount,
            FirmBankingRequest.FirmBankingStatus firmBankingStatus,
            FirmBankingRequest.FirmbankingAggregateIdentifier firmBankingAggregateIdentifier
    );

    FirmBankingRequestJpaEntity modifyFirmBankingRequest(
            FirmBankingRequestJpaEntity entity
    );

    FirmBankingRequestJpaEntity getFirmBankingRequest(
            FirmBankingRequest.FirmbankingAggregateIdentifier firmBankingAggregateIdentifier
    );
}
