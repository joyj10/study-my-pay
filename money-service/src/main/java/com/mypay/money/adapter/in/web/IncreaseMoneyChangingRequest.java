package com.mypay.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncreaseMoneyChangingRequest {
    // 무조건 증액 요청 (충전)
    private String targetMembershipId;
    private int amount;
}
