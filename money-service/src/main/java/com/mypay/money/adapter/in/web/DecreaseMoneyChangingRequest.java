package com.mypay.money.adapter.in.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DecreaseMoneyChangingRequest {
    // 무조건 감액 요청 (이체)
    private String targetMembershipId;
    private int amount;
}
