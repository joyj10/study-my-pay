package com.mypay.remittanceservice.adapter.out.service.banking;

import com.mypay.common.CommonHttpClient;
import com.mypay.common.annotation.ExternalSystemAdapter;
import com.mypay.remittanceservice.application.port.out.banking.BankingInfo;
import com.mypay.remittanceservice.application.port.out.banking.BankingPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankingServiceAdapter implements BankingPort {

    private final CommonHttpClient bankingServiceHttpClient;

    @Value("${service.banking.url}")
    private String bankingServiceEndpoint;


    @Override
    public BankingInfo getMembershipBankingInfo(String bankName, String bankAccountNumber) {

        return null;
    }

    @Override
    public boolean requestFirmBanking(String bankName, String bankAccountNumber, int amount) {
        return false;
    }
}
