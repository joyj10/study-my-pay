package com.mypay.money.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MoneyChangingStatus {
    REQUESTED(0),  // 요청됨
    SUCCEEDED(1),  // 성공
    FAILED(2),     // 실패
    CANCELLED(3)   // 취소됨
    ;

    private final int code;

    public static MoneyChangingStatus from(int code) {
        return Arrays.stream(MoneyChangingStatus.values())
                .filter(status -> status.getCode() == code)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
