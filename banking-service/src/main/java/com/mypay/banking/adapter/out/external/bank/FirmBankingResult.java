package com.mypay.banking.adapter.out.external.bank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FirmBankingResult {
    private int resultCode; // 200:성공, 0:실패
}
