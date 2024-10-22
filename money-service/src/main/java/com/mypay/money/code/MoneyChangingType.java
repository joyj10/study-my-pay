package com.mypay.money.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MoneyChangingType {
    INCREASING(0), // 증액
    DECREASING(1)  // 감액
    ;

    private final int code;

    public static MoneyChangingType from(int code) {
        return Arrays.stream(MoneyChangingType.values())
                .filter(type -> type.getCode() == code)
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
