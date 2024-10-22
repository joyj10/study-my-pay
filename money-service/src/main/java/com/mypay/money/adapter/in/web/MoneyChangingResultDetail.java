package com.mypay.money.adapter.in.web;

import com.mypay.money.code.MoneyChangingResultStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MoneyChangingResultDetail {
    private String moneyChangingRequestId;

    // 증액, 감액
    private int moneyChangingType;          // 0: 증액, 1: 감액
    private MoneyChangingResultStatus moneyChangingResultStatus;
    private int amount;
}
