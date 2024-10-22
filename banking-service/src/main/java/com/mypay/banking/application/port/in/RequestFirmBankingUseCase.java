package com.mypay.banking.application.port.in;

import com.mypay.banking.domain.FirmBankingRequest;
import com.mypay.common.UseCase;

@UseCase
public interface RequestFirmBankingUseCase {
    FirmBankingRequest requestFirmBanking(RequestFirmBankingCommand command);
}
