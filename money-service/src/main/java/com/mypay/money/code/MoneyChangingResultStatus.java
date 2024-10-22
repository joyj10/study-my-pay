package com.mypay.money.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MoneyChangingResultStatus {
    SUCCEEDED,  // 성공
    FAILED,     // 실패
    FAILED_NOT_ENOUGH_MONEY,        // 실패 - 잔액 부족
    FAILED_NOT_EXIST_MEMBERSHIP,    // 실패 - 멤버십 없음
    FAILED_NOT_EXIST_MONEY_CHANGING_REQUEST    // 실패 - 머니 변액 요청 없음
    ;

    public static MoneyChangingResultStatus from(MoneyChangingStatus moneyChangingStatus) {
        return moneyChangingStatus == MoneyChangingStatus.SUCCEEDED ? SUCCEEDED : FAILED;
    }
}
