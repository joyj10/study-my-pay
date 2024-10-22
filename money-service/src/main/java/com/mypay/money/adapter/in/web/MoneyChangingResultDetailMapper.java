package com.mypay.money.adapter.in.web;

import com.mypay.money.code.MoneyChangingResultStatus;
import com.mypay.money.domain.MoneyChangingRequest;
import org.springframework.stereotype.Component;

@Component
public class MoneyChangingResultDetailMapper {
    public MoneyChangingResultDetail mapToMoneyChangingResultDetail(MoneyChangingRequest request) {
        return MoneyChangingResultDetail.builder()
                .moneyChangingRequestId(request.getMoneyChangingRequestId())
                .moneyChangingType(request.getChangingType().getCode())
                .moneyChangingResultStatus(MoneyChangingResultStatus.from(request.getChangingMoneyStatus()))
                .amount(request.getChangingMoneyAMount())
                .build();
    }
}
