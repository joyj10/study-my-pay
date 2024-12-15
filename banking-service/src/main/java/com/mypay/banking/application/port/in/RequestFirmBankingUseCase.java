package com.mypay.banking.application.port.in;

import com.mypay.banking.domain.FirmBankingRequest;
import com.mypay.common.annotation.UseCase;

@UseCase
public interface RequestFirmBankingUseCase {
    FirmBankingRequest requestFirmBanking(RequestFirmBankingCommand command);
    void requestFirmBankingByEvent(RequestFirmBankingCommand command);
}
