package com.mypay.remittanceservice.application.port.out.banking;

public interface BankingPort {
    BankingInfo getMembershipBankingInfo(String bankName, String bankAccountNumber);

    boolean requestFirmBanking(String bankName, String bankAccountNumber, int amount);
}
