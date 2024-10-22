package com.mypay.money.application.port.in;

import com.mypay.money.domain.MoneyChangingRequest;
import com.mypay.common.UseCase;

@UseCase
public interface IncreaseMoneyRequestUseCase {
    MoneyChangingRequest increaseMoneyRequest(IncreaseMoneyRequestCommand command);
}
