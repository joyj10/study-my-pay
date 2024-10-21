package com.mypay.banking.adapter.out.external.bank;

import com.mypay.banking.application.port.out.RequestBankAccountInfoPort;
import com.mypay.common.ExternalSystemAdapter;
import lombok.RequiredArgsConstructor;

@ExternalSystemAdapter
@RequiredArgsConstructor
public class BankAccountAdapter implements RequestBankAccountInfoPort {


    @Override
    public BankAccount getBankAccountInfo(GetBankAccountRequest request) {
        // 실제 로직: 실제로 외부 은행 API 호출 -> 계좌 정보 가져옴 -> parsing -> 데이터 리턴

        // 외부 통신 가정한 예시 케이스
        return new BankAccount(request.getBankName(), request.getBankAccountNumber(), true);
    }
}
