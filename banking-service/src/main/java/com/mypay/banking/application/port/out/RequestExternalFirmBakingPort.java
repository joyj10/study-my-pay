package com.mypay.banking.application.port.out;

import com.mypay.banking.adapter.out.external.bank.ExternalFirmBankingRequest;
import com.mypay.banking.adapter.out.external.bank.FirmBankingResult;

public interface RequestExternalFirmBakingPort {
    FirmBankingResult requestExternalFirmBanking(ExternalFirmBankingRequest request);
}
